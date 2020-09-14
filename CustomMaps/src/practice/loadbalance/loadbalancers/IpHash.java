package practice.loadbalance.loadbalancers;

import practice.loadbalance.IpPool;
import practice.loadbalance.LoadBalance;

import java.util.ArrayList;
import java.util.List;

public class IpHash implements LoadBalance {
    private final Object lockObj = new Object();
    List<String> serverList;

    public IpHash() {
        serverList = new ArrayList<>(IpPool.getServers());
    }

    @Override
    public String getServer(String clientIp) {
        int index = clientIp.hashCode() % serverList.size();
        return serverList.get(index);
    }
}
