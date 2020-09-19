package com.arindam.das.imc.model.player;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.assets.MoveStrategy;
import com.arindam.das.imc.core.assets.strategies.FrequencyCounting;
import com.arindam.das.imc.core.assets.strategies.PatternMatching;
import com.arindam.das.imc.core.assets.strategies.Randomized;
import com.arindam.das.imc.core.assets.strategies.Rotation;
import com.arindam.das.imc.core.factory.MoveFactory;
import com.arindam.das.imc.model.Move;
import com.arindam.das.imc.model.Player;
import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Computer extends Player {

    private static final String name = "Computer";
    private final MoveStrategy[] moveStrategies;
    private final Map<MoveType, Move> moveMap;
    private MoveStrategy lastUsedStrategy;

    public Computer() {
        super(name);
        moveStrategies = new MoveStrategy[4];
        moveStrategies[3] = new MoveStrategy(new Randomized(), 1.0);
        moveStrategies[2] = new MoveStrategy(new Rotation(), 2.0);
        moveStrategies[1] = new MoveStrategy(new FrequencyCounting(), 4.0);
        moveStrategies[0] = new MoveStrategy(new PatternMatching(), 8.0);
        moveMap = new HashMap<>();
        for(MoveType moveType: MoveType.values()) {
            moveMap.put(moveType, MoveFactory.create(moveType));
        }
    }

    private MoveStrategy selectStrategy() {
        MoveStrategy selected = null;
        for(MoveStrategy moveStrategy: moveStrategies) {
            if(moveStrategy.getStrategy().ready() && moveStrategy.getStrategy().suggestOpponentMove()!=null) {
                selected = moveStrategy;
                break;
            }
        }
        System.out.println("Computer selected strategy : " + selected.getStrategy().getName() + ", " + selected.getStrategy().ready());
        String strategies = "";
        for(int i=0; i<4; i++) {
            strategies = strategies + String.format(" %s, %s : %.8f",
                    moveStrategies[i].getStrategy().getName(), moveStrategies[i].getStrategy().ready(), moveStrategies[i].getScore());
        }
        System.out.println(strategies);
        return selected;
    }

    public MoveType move() {
        return moveMap.get(lastUsedStrategy.getStrategy().suggestOpponentMove()).losesTo();
    }

    public void consumeOpponentInput(MoveType moveType, boolean win) {
        for(MoveStrategy moveStrategy: moveStrategies) {
            moveStrategy.getStrategy().consume(moveType);
            moveStrategy.decayScore();
        }
        if(win) {
            lastUsedStrategy.incrementScore();
        }
        Arrays.sort(moveStrategies, (o1, o2) -> o2.getScore().compareTo(o1.getScore()));
        lastUsedStrategy = selectStrategy();
    }

    @VisibleForTesting
    protected MoveStrategy getLastUsedStrategy() {
        return lastUsedStrategy;
    }
}
