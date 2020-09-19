package com.arindam.das.imc.core.assets.strategies;

import com.arindam.das.imc.exceptions.StrategyNotReadyException;
import com.arindam.das.imc.common.MoveType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RotationStrategyTest {

    private Rotation rotation;

    @Before
    public void start() {
        rotation = new Rotation();
    }

    @Test(expected = StrategyNotReadyException.class)
    public void TestNotReady() {
        assert !rotation.ready();
        rotation.consume(MoveType.scissors);
        assert !rotation.ready();
        rotation.suggestOpponentMove();
    }

    @Test
    public void TestRotation() {
        assert rotation.getName().equals("Rotation");
        assert !rotation.ready();
        rotation.consume(MoveType.scissors);
        assert !rotation.ready();
        rotation.consume(MoveType.rock);
        assert rotation.ready();
        assert rotation.suggestOpponentMove()==MoveType.paper;
        rotation.consume(MoveType.scissors);
        assert rotation.suggestOpponentMove()==MoveType.paper;
        rotation.consume(MoveType.paper);
        assert rotation.suggestOpponentMove()==MoveType.rock;
        rotation.consume(MoveType.scissors);
        assert rotation.suggestOpponentMove()==MoveType.rock;
    }

    @After
    public void tearDown(){
    }
}
