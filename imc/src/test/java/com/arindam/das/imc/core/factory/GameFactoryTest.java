package com.arindam.das.imc.core.factory;

import com.arindam.das.imc.common.GameType;
import com.arindam.das.imc.model.Game;
import org.junit.Before;
import org.junit.Test;

public class GameFactoryTest {

    @Before
    public void start() { }

    @Test
    public void TestVsHumanGame() {
        Game game = GameFactory.create(GameType.vsHuman);

    }

    @Test
    public void TestVsComputerGame() {
        Game game = GameFactory.create(GameType.vsComputer);
    }
}
