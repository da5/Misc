package com.arindam.das.imc.core.assets;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MoveStrategy {
    private final Strategy strategy;
    private Double score;

    public void decayScore() {
        this.score *= 0.9;
    }

    public void incrementScore(int reward) {
        this.score += reward;
    }
}
