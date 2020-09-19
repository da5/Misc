package com.arindam.das.imc.model.moves;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.model.Move;

public class Paper extends Move {
    @Override
    public MoveType getMoveType() {
        return MoveType.paper;
    }
}
