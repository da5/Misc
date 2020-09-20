package com.arindam.das.imc.core.manager;

import com.arindam.das.imc.common.CommandLineInput;
import com.arindam.das.imc.common.MenuType;
import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ModeratorTest {

    Game game;
    Moderator moderator;
    CommandLineInput commandLineInput;

    @Before
    public void start() {
        moderator = Moderator.getInstance();
        game = Mockito.mock(Game.class);
        commandLineInput = Mockito.mock(CommandLineInput.class);
        moderator.setCommandLineInput(commandLineInput);
    }

    @Test
    public void TestSingleton() {
        assert moderator==Moderator.getInstance();
    }

    @Test
    public void TestLifeCycle() {
        assert moderator.isPower();
        assert moderator.getMenuType().equals(MenuType.GameMenu);
        Mockito.when(commandLineInput.getUserInput()).thenReturn("C");
        moderator.gameMenu();
        assert moderator.getMenuType().equals(MenuType.MoveMenu);

        Mockito.when(game.performMove()).thenReturn(new Game.GameMove(MoveType.scissors, MoveType.rock, new int[]{0,1}));
        moderator.moveMenu(game);
        assert moderator.getMenuType().equals(MenuType.MoveMenu);

        Mockito.when(game.performMove()).thenReturn(null);
        moderator.moveMenu(game);
        assert moderator.getMenuType().equals(MenuType.GameMenu);

        Mockito.when(commandLineInput.getUserInput()).thenReturn("X");
        moderator.gameMenu();
        assert moderator.getMenuType().equals(MenuType.ExitMenu);
        moderator.exitMenu();
        assert !moderator.isPower();
    }

}
