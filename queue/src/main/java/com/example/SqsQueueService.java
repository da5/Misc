package com.example;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SqsQueueService implements QueueService {
    final long visibility = 30; //in seconds
    AmazonSQS sqsClient;
    Map<String, String> queueUrlMap;
    final static String VISIBILITY_TIMEOUT = "VisibilityTimeout";

    public SqsQueueService(AmazonSQSClient sqsClient) {
      this.sqsClient = sqsClient;
      queueUrlMap = new HashMap<>();
    }

    private String create(String queue, Long visibility){
        if(!queueUrlMap.containsKey(queue)){
            CreateQueueResult result;
            try{
                result = sqsClient.createQueue(
                        new CreateQueueRequest(queue).addAttributesEntry(VISIBILITY_TIMEOUT, visibility.toString())
                );
            }catch (AmazonClientException e){
                throw e;
            }
            queueUrlMap.put(queue, result.getQueueUrl());
        }
        return queueUrlMap.get(queue);
    }

    @Override
    public void push(Object message, String queue){
        String url = create(queue, visibility);
        SendMessageRequest request = new SendMessageRequest().withQueueUrl(url).withMessageBody(message.toString());
        try{
            sqsClient.sendMessage(request);
        }catch (AmazonClientException e){
            throw e;
        }
    }

    @Override
    public Object pull(String queue){
        Object returnObject = null;
        String url = queueUrlMap.get(queue);
        if(url != null){
            try{
                List<Message> messages = sqsClient.receiveMessage(url).getMessages();
                if(messages!=null && messages.size()>0){
                    returnObject = messages.get(0);
                }
            }catch (AmazonClientException e){
                throw e;
            }
        }
        return returnObject;
    }

    @Override
    public void delete(Object message, String queue){
        String url = queueUrlMap.get(queue);
        if(url != null){
            try{
                sqsClient.deleteMessage(url, ((Message)message).getReceiptHandle());
            }catch (AmazonClientException e){
                throw e;
            }
        }
    }
}
