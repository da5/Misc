package com.arindam.das.imc.model.player;

import com.arindam.das.imc.common.CommandLineInput;
import com.arindam.das.imc.common.MoveType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UserTest {
    User user;
    CommandLineInput commandLineInput;

    @Before
    public void start() {
        commandLineInput = Mockito.mock(CommandLineInput.class);
        Mockito.when(commandLineInput.getUserInput()).thenReturn("R");
        user = new User("Player1", commandLineInput);
    }

    @Test
    public void TestUserMove() {
        user.consumeOpponentInput(MoveType.scissors, 1);
        assert user.move().equals(MoveType.rock);
    }
}
