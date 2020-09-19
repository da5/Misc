package com.arindam.das.imc.core.assets;

import com.arindam.das.imc.common.MoveType;

public interface Strategy {
    public void consume(MoveType moveType);
    public MoveType suggestOpponentMove();
    public boolean ready();
    public String getName();
}
