package com.arindam.das.imc.core.assets;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
    Used by the Computer to keep a track of the various strategies under its sleeves
    and how (un)successful they have been so far.
 */
@AllArgsConstructor
@Getter
public class MoveStrategy {
    private final Strategy strategy;
    private Double score;

    //to discard away old/stale score and pick the strategy more on the recent outcome
    public void decayScore() {
        this.score *= 0.9;
    }

    //to move a strategy up/down in the preference order based on its success/failure
    public void incrementScore(int reward) {
        this.score += reward;
    }
}
