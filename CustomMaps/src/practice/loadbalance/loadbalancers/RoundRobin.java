package practice.loadbalance.loadbalancers;

import practice.loadbalance.IpPool;
import practice.loadbalance.LoadBalance;

import java.util.ArrayList;
import java.util.List;

public class RoundRobin implements LoadBalance {
    private static Integer position = 0;
    private final Object lockObj = new Object();
    List<String> serverList;

    public RoundRobin() {
        serverList = new ArrayList<>(IpPool.getServers());
    }

    @Override
    public String getServer(String clientIp) {

        String target = null;

        synchronized (lockObj) {
            if (position > serverList.size() - 1) {
                position = 0;
            }
            target = serverList.get(position);
            position++;
        }
        return target;
    }
}
