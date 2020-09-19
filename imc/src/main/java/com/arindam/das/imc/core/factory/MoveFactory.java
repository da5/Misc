package com.arindam.das.imc.core.factory;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.model.Move;
import com.arindam.das.imc.model.moves.Paper;
import com.arindam.das.imc.model.moves.Rock;
import com.arindam.das.imc.model.moves.Scissors;

public class MoveFactory {
    public static Move create(MoveType moveType) {
        Move move = null;
        switch (moveType) {
            case paper:
                move = new Paper();
                break;
            case rock:
                move = new Rock();
                break;
            case scissors:
                move = new Scissors();
                break;
        }
        return move;
    }
}
