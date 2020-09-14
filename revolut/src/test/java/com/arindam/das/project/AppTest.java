package com.arindam.das.project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class AppTest {

    App app;

    @Before
    public void setUp(){
        app = new App();
    }

    private int sumSeries(int n) {
        int sum = 0;
        for(int i=1; i<=n; i++) {
            sum += i;
        }
        return sum;
    }

    @Test
    public void test1() {
        for(int n=0; n<10; n++) {
            assert sumSeries(n)==app.sumSeries(n);
        }
    }

    @After
    public void tearDown(){
    }
}
