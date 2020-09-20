package com.arindam.das.imc.model;

import com.arindam.das.imc.common.MoveType;
import lombok.Getter;

@Getter
public abstract class Player {
    private final String name;
    private int score;

    public Player(String name) {
        this.name = name;
    }

    public void addScore(int x) {
        score += x;
    }

    public abstract MoveType move();

    public abstract void consumeOpponentInput(MoveType moveType, int reward);
}
