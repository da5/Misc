package PubSub;

public class SimpleConsumer extends Consumer {

    public SimpleConsumer(int id) {
        super(id);
    }

    public void process(Message message) {
        System.out.println(String.format("Consumer %d processing message %s", id, message));
    }
}
