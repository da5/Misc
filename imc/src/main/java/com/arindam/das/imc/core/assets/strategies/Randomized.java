package com.arindam.das.imc.core.assets.strategies;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.assets.Strategy;
import com.google.common.annotations.VisibleForTesting;

import java.util.Random;

public class Randomized implements Strategy {

    private final Random random;
    private final boolean ready;
    private static final String name = "Randomized";

    public Randomized() {
        this.random = new Random();
        this.ready = true;
    }

    public void consume(MoveType moveType) {
        //do nothing
    }

    @VisibleForTesting
    protected void setCustomSeed(long seed) {
        this.random.setSeed(seed);
    }

    public MoveType suggestOpponentMove() {
        int possibleMoves = MoveType.values().length;
        return MoveType.values()[random.nextInt(possibleMoves)];
    }

    public boolean ready() {
        return this.ready;
    }

    public String getName() {
        return name;
    }
}
