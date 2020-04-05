package PubSub;

/*
publisher1 publishes message, message2 to channel1
consumer1, consumer2 consuming from channel1
 */
public class Driver {
    public static void main(String[] args) {
        Publisher publisher = new SimplePublisher(1);


        Consumer consumer1 = new SimpleConsumer(1);
        Consumer consumer2 = new SimpleConsumer(2);
        consumer1.subscribe("channel1");
        consumer2.subscribe("channel1");

        try {
            publisher.publish("channel1", publisher.buildMessage("message1"));
            publisher.publish("channel1", publisher.buildMessage("message2"));

        } catch (Exception e) {

        }

        System.out.println(consumer1.consume("channel1"));
        System.out.println(consumer2.consume("channel1"));


    }
}
