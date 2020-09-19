package com.arindam.das.imc.model;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.factory.MoveFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MoveTest {

    Map<MoveType, Move> moveMap;

    @Before
    public void start() {
        moveMap = new HashMap<>();
        for(MoveType moveType: MoveType.values()) {
            moveMap.put(moveType, MoveFactory.create(moveType));
        }
    }

    @Test
    public void TestWinningMoves() {
        assert moveMap.get(MoveType.paper).beats(MoveType.rock);
        assert moveMap.get(MoveType.rock).beats(MoveType.scissors);
        assert moveMap.get(MoveType.scissors).beats(MoveType.paper);
    }

    @Test
    public void TestLosingMoves() {
        assert !moveMap.get(MoveType.rock).beats(MoveType.paper);
        assert !moveMap.get(MoveType.scissors).beats(MoveType.rock);
        assert !moveMap.get(MoveType.paper).beats(MoveType.scissors);
    }

    @After
    public void tearDown(){
    }
}
