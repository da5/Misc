package com.example;

import com.example.model.MessageObject;
import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.time.Clock;
import java.time.ZoneId;
import java.util.*;

public class FileQueueService implements QueueService {
    /*
        Though the code has the capability to maintain visibility at message level,
        this default value is used for every message pushed.
        Unlike AWS SQS, I am not maintaining visibility at the queue level.
     */
    final long visibility = 1000; //in milliseconds
//    final static String urlPrefix = "/tmp/sqs/";
    final static String CREATE_QUEUE = "CREATE_QUEUE";
    final static String FILE_DELIM = "~";


    /*
        The thread for the purpose of periodically purging deleted messages and restoring ones with visibility timeout
     */
    public class Disposer extends Thread{
        final long disposerPollingInterval = 500; //in milliseconds
        @Override
        public void run() {
            while(true){
                dispose();
                try {
                    System.out.println("Sleeping before next polling.");
                    Thread.sleep(disposerPollingInterval);
                }catch (InterruptedException e){

                }
            }
        }
    }

    /*
        A dedicated clock is used for implementing the visibility-timeout functionality; however, could have also be
         achieved with a DelayQueue in Java but then had to depend on sleep/wait in test cases.
     */
    private static Clock clock = Clock.systemDefaultZone();
    Thread disposer;
    String urlPrefix;

    public FileQueueService(String urlPrefix) {
        this.urlPrefix = urlPrefix;
        File file = new File(urlPrefix);
        if(file.exists()){
            deleteFileSystemDirectory(file);
        }
        file.mkdir();
        disposer = new Disposer();
        disposer.start();	//starting the thread and it will continue to monitor during the lifetime of the service
    }

    private void lock(File lock) throws InterruptedException{
        while (!lock.mkdir()){
            Thread.sleep(50);
        }
    }

    private void unlock(File lock){
        lock.delete();
    }

    private File getLockFile(String queue){
        return new File(getUrl(queue)+".lock/");
    }

    private File getMessageFile(String queue){
        /*
            message~visibility
         */
        return new File(getUrl(queue)+"messages");
    }

    private File getSuppressedFile(String queue){
        /*
            message~startTime~visibility
         */
        return new File(getUrl(queue)+"suppressed");
    }

    private File getMarkedFile(String queue){
        /*
            message
         */
        return new File(getUrl(queue)+"marked");
    }

    private String getUrl(String queue){
        return urlPrefix+queue+"/";
    }

    private File getFile(String url){
        return new File(url);
    }

    public void createQueue(String queue) {
        String url = getUrl(queue);
        File queueDir = getFile(url);
        if(!queueDir.exists()) {
            synchronized (CREATE_QUEUE) {
                if (!queueDir.exists()) {
                    queueDir.mkdir();
                    try {
                        getMessageFile(queue).createNewFile();
                        getSuppressedFile(queue).createNewFile();
                        getMarkedFile(queue).createNewFile();
                    }catch (IOException e){
                        queueDir.delete();
                        System.out.println("Failed to create queue " + queue + " " + e);
                    }
                }
            }
        }
    }

    @Override
    public void push(Object message, String queue){
        File queueFile = getFile(getUrl(queue));
        if(!queueFile.exists()){
            createQueue(queue);
        }
        pushSafe(new MessageObject(message, visibility), queue);
        System.out.println("Message "+ message + " sent to queue " + queue);
    }

    private void pushSafe(MessageObject messageObject, String queue){
        File lockFile = getLockFile(queue);
        File messageFile = getMessageFile(queue);
        try{
            lock(lockFile);
            appendToMessageFile(messageObject, messageFile);
        }catch (InterruptedException e){
            System.out.println("Could not lock queue " + queue + " for writing :: " + e);
        }catch (IOException e){
            System.out.println("Failed to push message onto queue " + queue + " :: " + e);
        }finally {
            unlock(lockFile);
        }

    }

    private void appendToMessageFile(MessageObject messageObject,File messageFile) throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter(messageFile, true));
        pw.println(messageObject.getMessage() + FILE_DELIM + messageObject.getVisibility());
        pw.close();
    }

    @Override
    public Object pull(String queue){
        Object returnObject = null;
        File queueFile = getFile(getUrl(queue));
        File lockFile = getLockFile(queue);
        File messageFile = getMessageFile(queue);
        File suppressedFile = getSuppressedFile(queue);
        if(queueFile.exists()){
            try {
                lock(lockFile);
                //read all lines
                BufferedReader bf = new BufferedReader(new FileReader(messageFile));
                String readLine;
                List<String> lines = new ArrayList<>();
                while ((readLine = bf.readLine())!=null){
                    lines.add(readLine);
                }
                bf.close();
                //print all but the first line
                PrintWriter pw = new PrintWriter(new FileWriter(messageFile, false));
                for(int i = 1; i<lines.size(); i++){
                    pw.println(lines.get(i));
                }
                pw.close();
                //return the first line
                String[] columns = (lines.get(0)).split(FILE_DELIM);
                returnObject = columns[0];
                pw = new PrintWriter(new FileWriter(suppressedFile, true));
                pw.println(columns[0] + FILE_DELIM + clock.millis()+ FILE_DELIM + columns[1]);
                pw.close();
            }catch (IOException e){
                System.out.println("Failed to poll message from queue " + queue + " :: " + e);
            }catch (InterruptedException e){
                System.out.println("Could not lock queue " + queue + " for polling :: " + e);
            }finally {
                unlock(lockFile);
            }
        }
        return returnObject;
    }

    @Override
    public void delete(Object message, String queue) {
        File queueFile = getFile(getUrl(queue));
        File lockFile = getLockFile(queue);
        File markedFile = getMarkedFile(queue);
        if (queueFile.exists()) {
            try {
                lock(lockFile);
                PrintWriter pw = new PrintWriter(new FileWriter(markedFile, true));
                pw.println(message);
                pw.close();
            } catch (IOException e) {
                System.out.println("Failed to mark message in queue " + queue + " :: " + e);
            } catch (InterruptedException e) {
                System.out.println("Could not lock queue " + queue + " for marking :: " + e);
            } finally {
                unlock(lockFile);
            }
        }
    }

    public int size(String queue) {
        File queueFile = getFile(getUrl(queue));
        File lockFile = getLockFile(queue);
        File messageFile = getMessageFile(queue);
        int size = -1;
        if (queueFile.exists()) {
            try {
                lock(lockFile);
                BufferedReader bf = new BufferedReader(new FileReader(messageFile));
                String readLine;
                List<String> lines = new ArrayList<>();
                while ((readLine = bf.readLine()) != null) {
                    lines.add(readLine);
                }
                bf.close();
                size = lines.size();
            } catch (IOException e) {
                System.out.println("Failed to count message in queue " + queue + " :: " + e);
            } catch (InterruptedException e) {
                System.out.println("Could not lock queue " + queue + " for counting :: " + e);
            } finally {
                unlock(lockFile);
            }
        }
        return size;
    }

    public List<String> getQueueNames(){
        File[] files = (new File(urlPrefix)).listFiles(File::isDirectory);
        List<String> queueUrls = new ArrayList<>();
        for(int i=0; i<files.length; i++){
            queueUrls.add(files[i].getName());
        }
        return queueUrls;
    }
    /*
        Method to be only used by the test-class to simulate advancing the class clock
     */
    @VisibleForTesting
    protected void addMillisecondsToClock(long millis){
        System.out.println("[Testing] Before adding millis : " + clock.millis());
        clock = Clock.fixed(clock.instant().plusMillis(millis), ZoneId.systemDefault());
        System.out.println("[Testing] After adding millis : " + clock.millis());
    }


    private void deleteFileSystemDirectory(File file){
        if(file.exists()){
            deleteRecursive(file);
        }
    }

    private void deleteRecursive(File file){
        if(file.isDirectory()){
            for(File subFile: file.listFiles()){
                deleteRecursive(subFile);
            }
        }
        file.delete();
    }

    /*
        Method is internally called by disposer-thread which purges the ones marked-to-be-deleted and
        restoring the ones with visibility timeout
        Also, it is made available for test-class for simulating behavior
     */
    @VisibleForTesting
    protected void dispose(){
        System.out.println("Disposing at " + clock.millis());
        List<String> queueNames = getQueueNames();
        for(String queue: queueNames){
            File lockFile = getLockFile(queue);
            try {
                lock(lockFile);
                List<MessageObject> suppressedMessages = readSuppressedMessages(queue);
                Set<String> markedMessages = readMarkedMessages(queue);
                List<MessageObject> expiredTimeoutMessages = new ArrayList<>();
                for(MessageObject messageObject: suppressedMessages){
                    if(clock.millis() > messageObject.getStartTime()+messageObject.getVisibility()
                            && !markedMessages.contains(messageObject.getMessage())){
                        expiredTimeoutMessages.add(messageObject);
                    }
                }
                restoreExpiredMessages(expiredTimeoutMessages, queue);
                updateSuppressedMessages(suppressedMessages, markedMessages, expiredTimeoutMessages, queue);
                clearMarkedMessages(queue);
            } catch (IOException e) {
                System.out.println("Failed to read suppressed/marked messages for " + queue + " :: " + e);
            }catch (InterruptedException e){
                System.out.println("Could not lock queue " + queue + " for disposing :: " + e);
            }finally {
                unlock(lockFile);
            }
        }
    }

    private void updateSuppressedMessages(List<MessageObject> suppressedMessages, Set<String> markedMessages,
                                          List<MessageObject> expiredTimeoutMessages, String queue) throws IOException{
        for(MessageObject messageObject: expiredTimeoutMessages){
            markedMessages.add(messageObject.getMessage().toString());
        }
        File suppressedFile = getSuppressedFile(queue);
        PrintWriter pw = new PrintWriter(new FileWriter(suppressedFile, false));
        for(MessageObject messageObject: suppressedMessages){
            if(!markedMessages.contains(messageObject.getMessage().toString())){
                pw.println(messageObject.getMessage()+FILE_DELIM+ messageObject.getStartTime() +FILE_DELIM+ messageObject.getVisibility());
            }
        }
        pw.close();
    }

    private void clearMarkedMessages(String queue) throws IOException{
        File markedFile = getMarkedFile(queue);
        PrintWriter pw = new PrintWriter(new FileWriter(markedFile, false));
        pw.write("");
        pw.close();
    }

    private void restoreExpiredMessages(List<MessageObject> expiredTimeoutMessages, String queue) throws IOException{
        for(MessageObject messageObject: expiredTimeoutMessages){
            appendToMessageFile(messageObject, getMessageFile(queue));
        }
    }

    private List<MessageObject> readSuppressedMessages(String queue) throws IOException{
        File suppressedFile = getSuppressedFile(queue);
        List<MessageObject> messageObjects = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader(suppressedFile));
        String readLine;
        while ((readLine = bf.readLine()) != null) {
            String[] columns = readLine.split(FILE_DELIM);
            messageObjects.add(new MessageObject(columns[0], Long.parseLong(columns[1]), Long.parseLong(columns[2])));
        }
        bf.close();
        return messageObjects;
    }

    private Set<String> readMarkedMessages(String queue) throws IOException{
        Set<String> markedMessages = new HashSet<>();
        File markedFile = getMarkedFile(queue);
        BufferedReader bf = new BufferedReader(new FileReader(markedFile));
        String readLine;
        while ((readLine = bf.readLine()) != null) {
            markedMessages.add(readLine);
        }
        bf.close();
        return markedMessages;
    }
}
