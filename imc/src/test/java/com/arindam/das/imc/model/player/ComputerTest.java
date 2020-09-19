package com.arindam.das.imc.model.player;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.assets.Strategy;
import com.arindam.das.imc.core.assets.strategies.FrequencyCounting;
import com.arindam.das.imc.core.assets.strategies.PatternMatching;
import com.arindam.das.imc.core.assets.strategies.Randomized;
import com.arindam.das.imc.core.assets.strategies.Rotation;
import com.arindam.das.imc.core.factory.MoveFactory;
import com.arindam.das.imc.model.Move;
import org.junit.Before;
import org.junit.Test;

public class ComputerTest {
    private Computer computer;
    private Strategy randomized;
    private Strategy rotation;
    private Strategy frequency;
    private Strategy pattern;

    @Before
    public void start() {
        computer = new Computer();
        randomized = new Randomized();
        rotation = new Rotation();
        frequency = new FrequencyCounting();
        pattern = new PatternMatching();
    }

    @Test
    public void testStrategySelection() {
        computer.consumeOpponentInput(MoveType.scissors, false);
        assert computer.getLastUsedStrategy().getStrategy().getName().equals(randomized.getName());

        computer.consumeOpponentInput(MoveType.scissors, false);
        assert computer.getLastUsedStrategy().getStrategy().getName().equals(rotation.getName());


        computer.consumeOpponentInput(MoveType.scissors, false);
        assert computer.getLastUsedStrategy().getStrategy().getName().equals(rotation.getName());


        computer.consumeOpponentInput(MoveType.scissors, false);
        assert computer.getLastUsedStrategy().getStrategy().getName().equals(rotation.getName());


        computer.consumeOpponentInput(MoveType.scissors, false);
        assert computer.getLastUsedStrategy().getStrategy().getName().equals(frequency.getName());

        computer.consumeOpponentInput(MoveType.scissors, false);
        assert computer.getLastUsedStrategy().getStrategy().getName().equals(frequency.getName());

        computer.consumeOpponentInput(MoveType.scissors, false);
        assert computer.getLastUsedStrategy().getStrategy().getName().equals(pattern.getName());

        computer.consumeOpponentInput(MoveType.scissors, false);
        assert computer.getLastUsedStrategy().getStrategy().getName().equals(pattern.getName());

        computer.consumeOpponentInput(MoveType.scissors, true);
        assert computer.getLastUsedStrategy().getStrategy().getName().equals(pattern.getName());

        Move move = MoveFactory.create(computer.move());
        assert move.beats(computer.getLastUsedStrategy().getStrategy().suggestOpponentMove());

        computer.consumeOpponentInput(MoveType.scissors, false);
        assert computer.getLastUsedStrategy().getStrategy().getName().equals(pattern.getName());

    }

}
