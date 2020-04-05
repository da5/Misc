package PubSub;

public abstract class Consumer {
    protected int id;
    protected Orchestrator orchestrator;

    public Consumer(int id) {
        this.id = id;
        this.orchestrator = Orchestrator.getInstance();
    }

    public int getId() {
        return id;
    }

    public boolean subscribe(String channel) {
        return orchestrator.subscribe(channel, id);
    }

    public boolean rewind(String channel, int offset) {
        return orchestrator.rewind(channel, id, offset);
    }

    public Message consume(String channel) {
        Message message = orchestrator.consume(channel, id);
        process(message);
        return message;
    }

    public abstract void process(Message message);
}
