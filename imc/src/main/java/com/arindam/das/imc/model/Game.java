package com.arindam.das.imc.model;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.factory.MoveFactory;
import com.google.common.annotations.VisibleForTesting;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/*
    The class Game manages a game between 2 Players and uses the Player method abstractions to perform the same. As a result,
    it's capable of hosting a game between a human/computer with another human/computer.

    The variable maxMoves serves for the variable `n` for which a game to be run. I have decided not to take it as an input
    from the user for the same of simplicity in the command-line menu. However, an user can input any invalid move character
    viz. 'x','v', etc to move out of the game on being prompted.
 */
public class Game {

    @AllArgsConstructor
    @Getter
    public static class GameMove {
        private final MoveType player1Move;
        private final MoveType player2Move;
        private final int[] score;

        @Override
        public String toString() {
            return String.format(" %s vs %s, [ %d, %d ]", player1Move, player2Move, score[0], score[1]);
        }
    }

    private final Player player1;
    private final Player player2;
//    private final List<GameMove> gameMoves;
    private final Map<MoveType, Move> moveMap;
    private int maxMoves;
    private int numberOfMoves;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
//        this.gameMoves = new ArrayList<>();
        moveMap = new HashMap<>();
        for(MoveType moveType: MoveType.values()) {
            moveMap.put(moveType, MoveFactory.create(moveType));
        }
        this.numberOfMoves = 0;
        this.maxMoves = 20;
    }

    public GameMove performMove() {
        if(numberOfMoves==maxMoves) {
            return null;
        }
        MoveType player1Move = player1.move();
        MoveType player2Move = player2.move();

        if(player1Move==null || player2Move==null) {
            return null;
        }

        boolean player1win = moveMap.get(player1Move).beats(player2Move);
        boolean player2win = moveMap.get(player2Move).beats(player1Move);
        player1.addScore(player1win? 1: 0);
        player2.addScore(player2win? 1: 0);

        GameMove gameMove = new GameMove(player1Move, player2Move, new int[]{player1.getScore(), player2.getScore()});
//        gameMoves.add(gameMove);

        numberOfMoves++;
        player1.consumeOpponentInput(player2Move, player1win? 1 : (player2win? -1 : 0));
        player2.consumeOpponentInput(player1Move, player2win? 1 : (player1win? -1 : 0));
        return gameMove;
    }

//    public List<String> getGameSummary() {
//        List<String> summary = new ArrayList<>();
//        gameMoves.forEach(gameMove -> summary.add(gameMove.toString()));
//        summary.add(getScore());
//        return summary;
//    }

    public String getScore() {
        return String.format(" %s [ %d : %d ] %s ", player1.getName(), player1.getScore(), player2.getScore(), player2.getName());
    }

    @VisibleForTesting
    protected void setMaxMoves(int maxMoves) {
        this.maxMoves = maxMoves;
    }
}
