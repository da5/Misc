package com.arindam.das.project.loadbalance;

import com.arindam.das.project.App;
import com.arindam.das.project.loadbalance.loadbalancers.IpHash;
import com.arindam.das.project.loadbalance.loadbalancers.RoundRobin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LoadBalanceTest {

    private App app;
    private LoadBalance roundRobin;
    private LoadBalance ipHash;


    @Before
    public void setUp() {
        app = Mockito.mock(App.class);
        Mockito.when(app.sumSeries(Mockito.anyInt())).thenReturn(1);
        roundRobin = new RoundRobin();
        ipHash = new IpHash();
    }

    @Test
    public void testRoundRobin() {
        int poolSize = IpPool.getServers().size();
        String[] ips = new String[poolSize];
        for(int i=0; i<poolSize; i++) {
            ips[i] = roundRobin.getServer("1.2.3.4");
        }
        for(int i=0; i<poolSize*3; i++) {
            assert ips[i%poolSize].equals(roundRobin.getServer("1.2.3.4"));
        }
    }

    @Test
    public void testIpHash() {
        assert ipHash.getServer("1.2.3.4").equals(ipHash.getServer("1.2.3.4"));
        assert ipHash.getServer("127.0.0.1").equals(ipHash.getServer("127.0.0.1"));
        assert app.sumSeries(10)==1;
    }

    @After
    public void tearDown(){
    }
}
