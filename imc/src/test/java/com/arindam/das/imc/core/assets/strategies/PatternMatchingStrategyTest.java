package com.arindam.das.imc.core.assets.strategies;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.exceptions.StrategyNotReadyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PatternMatchingStrategyTest {
    private PatternMatching patternMatching;

    @Before
    public void start() {
        patternMatching = new PatternMatching();
    }

    @Test(expected = StrategyNotReadyException.class)
    public void TestNotReady() {
        assert !patternMatching.ready();
        patternMatching.consume(MoveType.scissors);
        assert !patternMatching.ready();
        patternMatching.suggestOpponentMove();
    }

    @Test
    public void TestPatternMatching() {
        patternMatching.customizeWindowLengths(3,10);
        assert patternMatching.getName().equals("PatternMatching");

        assert !patternMatching.ready();
        patternMatching.consume(MoveType.rock);
        assert !patternMatching.ready();
        patternMatching.consume(MoveType.rock);
        assert !patternMatching.ready();
        patternMatching.consume(MoveType.scissors);

        assert !patternMatching.ready();
        patternMatching.consume(MoveType.scissors);

        assert patternMatching.ready();
        assert patternMatching.suggestOpponentMove()==null;

        patternMatching.consume(MoveType.paper);
        assert patternMatching.suggestOpponentMove()==null;

        patternMatching.consume(MoveType.scissors);
        assert patternMatching.suggestOpponentMove()==null;

        patternMatching.consume(MoveType.scissors);
        assert patternMatching.suggestOpponentMove()==null;

        patternMatching.consume(MoveType.paper);
        assert patternMatching.suggestOpponentMove()==MoveType.scissors;

        patternMatching.consume(MoveType.scissors);
        assert patternMatching.suggestOpponentMove()==MoveType.scissors;

        patternMatching.consume(MoveType.rock);
        assert patternMatching.suggestOpponentMove()==null;

        patternMatching.consume(MoveType.rock);
        assert patternMatching.suggestOpponentMove()==null;

        patternMatching.consume(MoveType.paper);
        assert patternMatching.suggestOpponentMove()==null;

        patternMatching.consume(MoveType.rock);
        assert patternMatching.suggestOpponentMove()==null;

        patternMatching.consume(MoveType.rock);
        assert patternMatching.suggestOpponentMove()==null;


    }

    @After
    public void tearDown(){
    }
}
