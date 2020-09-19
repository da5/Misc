package com.arindam.das.imc.core.assets.strategies;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.assets.Strategy;
import com.arindam.das.imc.exceptions.StrategyNotReadyException;
import com.google.common.annotations.VisibleForTesting;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FrequencyCounting implements Strategy {

    private int minWindowSize;
    private int maxWindowLength;
    private static final String name = "FrequencyCounting";
    private final Deque<MoveType> deque;
    private final Map<MoveType, Integer> frequency;

    public FrequencyCounting() {
        this.minWindowSize = 5;
        this.maxWindowLength = 50;
        this.deque = new LinkedList<>();
        this.frequency = new HashMap<>();
    }

    @VisibleForTesting
    protected void customizeWindowLengths(int minWindow, int maxWindow) {
        minWindowSize = minWindow;
        maxWindowLength = maxWindow;
    }

    public void consume(MoveType moveType) {
        deque.addLast(moveType);
        frequency.putIfAbsent(moveType, 0);
        int count = frequency.get(moveType);
        frequency.put(moveType, count+1);
        if(deque.size()>maxWindowLength) {
            MoveType staleMove = deque.removeFirst();
            count = frequency.get(staleMove);
            frequency.put(staleMove, count-1);
        }
    }

    public MoveType suggestOpponentMove() {
        if(!ready()) {
            throw new StrategyNotReadyException("Strategy is not yet ready to be used!");
        }
        MoveType opponentFav = null;
        int count = 0;
        for(MoveType moveType: MoveType.values()) {
            if(frequency.containsKey(moveType) && count < frequency.get(moveType) && frequency.get(moveType)>1) {
                count = frequency.get(moveType);
                opponentFav = moveType;
            }
        }

        return opponentFav;
    }

    public boolean ready() {
        return deque.size() >= minWindowSize;
    }

    public String getName() {
        return name;
    }
}
