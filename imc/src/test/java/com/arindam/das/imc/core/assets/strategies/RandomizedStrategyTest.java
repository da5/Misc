package com.arindam.das.imc.core.assets.strategies;

import com.arindam.das.imc.common.MoveType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class RandomizedStrategyTest {

    private Randomized randomized;
    private Random random;

    @Before
    public void start() {
        randomized = new Randomized();
        randomized.setCustomSeed(12345);

        random = new Random(12345);
    }

    @Test
    public void TestRandomized() {
        assert randomized.getName().equals("Randomized");
        int n = MoveType.values().length;
        assert randomized.ready();
        randomized.consume(MoveType.scissors);
        assert MoveType.values()[random.nextInt(n)]==randomized.suggestOpponentMove();
        randomized.consume(MoveType.rock);
        assert MoveType.values()[random.nextInt(n)]==randomized.suggestOpponentMove();
        randomized.consume(MoveType.paper);
        assert MoveType.values()[random.nextInt(n)]==randomized.suggestOpponentMove();
        randomized.consume(MoveType.paper);
        assert MoveType.values()[random.nextInt(n)]==randomized.suggestOpponentMove();
        randomized.consume(MoveType.scissors);
        assert MoveType.values()[random.nextInt(n)]==randomized.suggestOpponentMove();
    }

    @After
    public void tearDown(){
    }
}
