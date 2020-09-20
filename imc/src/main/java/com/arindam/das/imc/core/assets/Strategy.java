package com.arindam.das.imc.core.assets;

import com.arindam.das.imc.common.MoveType;

/*
    This is the abstraction for the different strategies used by the Computer. Although the different strategies
    viz. Randomized, Rotation, FrequencyCounting and PatternMatching were pretty intuitive, I got the idea of how they
    could be used in combination in a strategic way by studying the following resource on the internet:
        https://daniel.lawrence.lu/programming/rps/#s1
 */
public interface Strategy {
    //This facilitates in learning from the user input
    public void consume(MoveType moveType);

    //Based with(out) the previous moves of the opponent, try to guess their next input
    public MoveType suggestOpponentMove();

    //Based on the kind of algorithm, it might take some time before the knowledge from previous learning could be leveraged
    public boolean ready();

    public String getName();
}
