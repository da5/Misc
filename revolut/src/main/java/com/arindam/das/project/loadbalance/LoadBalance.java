package com.arindam.das.project.loadbalance;

public interface LoadBalance {
    String getServer(String clientIp);
}
