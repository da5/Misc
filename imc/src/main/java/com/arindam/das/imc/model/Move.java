package com.arindam.das.imc.model;

import com.arindam.das.imc.common.MoveType;
import lombok.Getter;

import java.util.*;

@Getter
public abstract class Move {
    private static final int possibleMoves = MoveType.values().length;
    private final Set<MoveType> beatsMoves;
    private final List<MoveType> losesMoves;
    private final Random random;

    public Move() {
        beatsMoves = new HashSet<>();
        losesMoves = new ArrayList<>();
        random = new Random();
        for(int i=1; i<=possibleMoves/2; i++) {
            int nextIdx = (getMoveType().ordinal()+i) % possibleMoves;
            beatsMoves.add( MoveType.values()[nextIdx] );
        }
        for(MoveType moveType: MoveType.values()) {
            if(!beatsMoves.contains(moveType) && !moveType.equals(getMoveType())) {
                losesMoves.add(moveType);
            }
        }
    }

    public final boolean beats(MoveType opponentMoveType) {
        return beatsMoves.contains(opponentMoveType);
    }

    public final MoveType losesTo() {
        int idx = random.nextInt(losesMoves.size());
        return losesMoves.get(idx);
    }

    public abstract MoveType getMoveType();
}
