package practice.thread.example1;

import java.util.concurrent.BlockingQueue;

/**
 * Created by arindam.das on 29/07/16.
 */

public class ItemObject<T>{
    T item;
    Object notificationObject;
    BlockingQueue<T> resultQueue;

    public BlockingQueue<T> getResultQueue() {
        return resultQueue;
    }

    public void setResultQueue(BlockingQueue<T> resultQueue) {
        this.resultQueue = resultQueue;
    }

    public ItemObject(T item, Object notificationObject,BlockingQueue<T> resultQueue ) {
        this.resultQueue = resultQueue;
        this.notificationObject = notificationObject;
        this.item = item;
    }


    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public Object getNotificationObject() {
        return notificationObject;
    }

    public void setNotificationObject(Object notificationObject) {
        this.notificationObject = notificationObject;
    }
}

