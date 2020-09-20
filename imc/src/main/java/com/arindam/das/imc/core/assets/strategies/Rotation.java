package com.arindam.das.imc.core.assets.strategies;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.assets.Strategy;
import com.arindam.das.imc.exceptions.StrategyNotReadyException;

public class Rotation implements Strategy {

    private boolean ready;
    private MoveType lastMove, nextMove;
    private static final String name = "Rotation";

    public Rotation() {
        ready = false;
    }

    public void consume (MoveType moveType) {
        if(lastMove!=null) {
            int n = MoveType.values().length;
            int offset = (moveType.ordinal()>lastMove.ordinal())?
                    moveType.ordinal()-lastMove.ordinal() : n-lastMove.ordinal()+moveType.ordinal();
            nextMove = MoveType.values()[(moveType.ordinal()+offset)%n];
            ready = true;
        }
        lastMove = moveType;
    }

    public MoveType suggestOpponentMove() {
        if(!ready) {
            throw new StrategyNotReadyException("Strategy is not yet ready to be used!");
        }
        return nextMove;
    }

    public boolean ready() {
        return ready;
    }

    public String getName() {
        return name;
    }
}
