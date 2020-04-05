package PubSub;

import java.util.Random;

public class SimplePublisher extends Publisher {
    public SimplePublisher(int id) {
        super(id);
    }

    public Message buildMessage(String content) {
        return new Message(1, content);
    }
}
