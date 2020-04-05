package PubSub;

public abstract class Publisher {
    private int id;
    protected Orchestrator orchestrator;

    public Publisher(int id) {
        this.id = id;
        this.orchestrator = Orchestrator.getInstance();
    }

    public int getId() {
        return id;
    }

    public abstract Message buildMessage(String content);

    public boolean createChannel(String channel) {
        return orchestrator.createChannel(channel);
    }

    public boolean publish(String channel, Message message) throws InterruptedException{
        return orchestrator.publish(channel, message);
    }
}
