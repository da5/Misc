package PubSub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Orchestrator {
    Map<String, List<Message>> channels;
    Map<String, Map<Integer, Integer>> consumerMap;
    Map<String, ReentrantLock> channelLocks;

    private static Orchestrator orchestrator;

    private Orchestrator() {
        Map<String, List<Message>> channels = new HashMap<>();
        channels.put("channel1", new ArrayList<>());
        channels.put("channel2", new ArrayList<>());

        consumerMap = new HashMap<>();
        channelLocks = new HashMap<>();
    }

    public static Orchestrator getInstance() {
        if(orchestrator==null) {
            synchronized (Orchestrator.class) {
                if(orchestrator==null) {
                    orchestrator = new Orchestrator();
                }
            }
        }
        return orchestrator;
    }

    protected boolean createChannel(String channelName) {
        if(channels.containsKey(channelName)) {
            return false;
        }
        channels.put(channelName, new ArrayList<>());
        consumerMap.put(channelName, new HashMap<>());
        channelLocks.put(channelName, new ReentrantLock());
        return true;
    }

    public boolean subscribe(String channel, int consumerId) {
        if(!consumerMap.containsKey(channel)) {
            return false;
        }
        if(consumerMap.get(channel).containsKey(consumerId)) {
            return false;
        }
        consumerMap.get(channel).put(consumerId, 0);
        return true;
    }

    public boolean rewind(String channel, int consumerId, int offset) {
        if(!consumerMap.containsKey(channel)) {
            return false;
        }
        if(!consumerMap.get(channel).containsKey(consumerId) || offset > consumerMap.get(channel).get(consumerId)) {
            return false;
        }
        consumerMap.get(channel).put(consumerId, offset);
        return true;
    }

    public Message consume(String channel, int consumerId) {
        if(!consumerMap.containsKey(channel)) {
            return null;
        }
        if(consumerMap.get(channel).containsKey(consumerId)) {
            return null;
        }
        int offset = consumerMap.get(channel).get(consumerId);
        Message message = channels.get(channel).get(offset);
        consumerMap.get(channel).put(consumerId, offset+1);
        return message;
    }

    public boolean publish(String channel, Message message) throws InterruptedException {
        if(!channels.containsKey(channel)) {
            return false;
        }
        boolean publish = false;
        int tries = 1;
        ReentrantLock lock = channelLocks.get(channel);
        while (tries<=3) {
            if(lock.tryLock()) {
                channels.get(channel).add(message);
                publish = true;
                break;
            } else {
                Thread.sleep(1000*tries);
                tries++;
            }
        }
        return publish;
    }
}
