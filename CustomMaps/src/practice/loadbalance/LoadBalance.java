package practice.loadbalance;

public interface LoadBalance {
    String getServer(String clientIp);
}
