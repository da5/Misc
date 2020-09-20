package com.arindam.das.imc.model;

import com.arindam.das.imc.common.MoveType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GameTest {
    Player player1;
    Player player2;
    Game game;

    @Before
    public void start() {
        player1 = Mockito.mock(Player.class);
        player2 = Mockito.mock(Player.class);
        Mockito.when(player1.move()).thenReturn(MoveType.rock);
        Mockito.when(player2.move()).thenReturn(MoveType.rock);
        Mockito.when(player1.getName()).thenReturn("player1");
        Mockito.when(player2.getName()).thenReturn("player2");
        Mockito.doCallRealMethod().when(player1).addScore(Mockito.anyInt());
        Mockito.doCallRealMethod().when(player2).addScore(Mockito.anyInt());
        Mockito.doCallRealMethod().when(player1).getScore();
        Mockito.doCallRealMethod().when(player2).getScore();

        game = new Game(player1, player2);
    }

    @Test
    public void testGameUserExit() {
        Game.GameMove gameMove = game.performMove();
        assert gameMove.getScore()[0]==0;
        assert gameMove.getScore()[1]==0;

        Mockito.when(player2.move()).thenReturn(MoveType.paper);
        gameMove = game.performMove();
        assert gameMove.getScore()[0]==0;
        assert gameMove.getScore()[1]==1;

        Mockito.when(player1.move()).thenReturn(MoveType.scissors);
        gameMove = game.performMove();
        assert gameMove.getScore()[0]==1;
        assert gameMove.getScore()[1]==1;

        Mockito.when(player1.move()).thenReturn(null);
        assert game.performMove()==null;
        assert game.getScore().equals(" player1 [ 1 : 1 ] player2 ");
    }

    @Test
    public void testGameExhaustedMoves() {
        Game.GameMove gameMove = game.performMove();
        assert gameMove.getScore()[0]==0;
        assert gameMove.getScore()[1]==0;

        Mockito.when(player2.move()).thenReturn(MoveType.paper);
        gameMove = game.performMove();
        assert gameMove.getScore()[0]==0;
        assert gameMove.getScore()[1]==1;

        game.setMaxMoves(2);
        assert game.performMove()==null;
    }
}


