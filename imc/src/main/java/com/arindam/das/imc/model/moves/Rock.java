package com.arindam.das.imc.model.moves;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.model.Move;

public class Rock extends Move {
    @Override
    public MoveType getMoveType() {
        return MoveType.rock;
    }
}
