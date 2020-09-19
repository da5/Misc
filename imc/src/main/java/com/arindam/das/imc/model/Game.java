package com.arindam.das.imc.model;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.factory.MoveFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    @AllArgsConstructor
    @Getter
    @ToString
    public static class GameMove {
        private final MoveType player1Move;
        private final MoveType player2Move;
        private final int[] score;
    }

    @Resource
    private final Player player1;
    @Resource
    private final Player player2;
    private final List<GameMove> gameMoves;
    private final Map<MoveType, Move> moveMap;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameMoves = new ArrayList<>();
        moveMap = new HashMap<>();
        for(MoveType moveType: MoveType.values()) {
            moveMap.put(moveType, MoveFactory.create(moveType));
        }
    }

    public GameMove performMove() {
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
        gameMoves.add(gameMove);

        player1.consumeOpponentInput(player2Move, player1win);
        player2.consumeOpponentInput(player1Move, player2win);
        return gameMove;
    }

//    public List<String> getGameSummary() {
//        List<String> summary = new ArrayList<>();
//        gameMoves.forEach(gameMove -> summary.add(gameMove.toString()));
//        summary.add(getScore());
//        return summary;
//    }

    public String getScore() {
        StringBuilder builder = new StringBuilder();
        builder.append(player1.getName()).append(" ").append(player1.getScore());
        builder.append(" : ");
        builder.append(player2.getScore()).append(" ").append(player2.getName());
        return builder.toString();
    }
}
