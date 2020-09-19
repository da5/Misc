package com.arindam.das.imc.model.player;

import com.arindam.das.imc.common.MoveType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class UserTest {
    User user;

    @Before
    public void start() {

    }

    @Test
    public void TestUserMove() {
        user = Mockito.mock(User.class);
        Mockito.when(user.move()).thenReturn(MoveType.rock);
        user.consumeOpponentInput(MoveType.scissors, true);
        assert user.move().equals(MoveType.rock);
    }

    @Test
    public void TestUserInput() {
        user = new User("Player-1");
        assert user.getName().equals("Player-1");
        String input = "R";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assert user.move().equals(MoveType.rock);
    }
}
