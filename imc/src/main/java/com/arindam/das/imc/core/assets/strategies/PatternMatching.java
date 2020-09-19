package com.arindam.das.imc.core.assets.strategies;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.assets.Strategy;
import com.arindam.das.imc.exceptions.StrategyNotReadyException;
import com.google.common.annotations.VisibleForTesting;

public class PatternMatching implements Strategy {
    private int windowSize;
    private int maxReferenceStringLength;
    private static final String name = "PatternMatching";

    private String referenceString;
    private String window;

    public PatternMatching() {
        this.windowSize = 5;
        this.maxReferenceStringLength = 50;
        this.referenceString = "";
        this.window = "";
    }

    @VisibleForTesting
    protected void customizeWindowLengths(int window, int maxRef) {
        windowSize = window;
        maxReferenceStringLength = maxRef;
    }

    public void consume(MoveType moveType) {
        referenceString = referenceString + moveType.label;
        window = window + moveType.label;
        if(referenceString.length() > maxReferenceStringLength + windowSize) {
            referenceString = referenceString.substring(windowSize);
        }
        if(window.length()>windowSize) {
            window = window.substring(1);
        }
    }

    public MoveType suggestOpponentMove() {
        if(!ready()) {
            throw new StrategyNotReadyException("Strategy is not yet ready to be used!");
        }
        MoveType opponentFav = null;
        int startIdx = 0;
        while(startIdx+windowSize < referenceString.length()-1) {
            int idx = referenceString.indexOf(window, startIdx);
            if(idx+windowSize<referenceString.length()-1) {
                opponentFav = MoveType.valueOfLabel(referenceString.substring(idx+windowSize, idx+windowSize+1));
            }
            startIdx += windowSize;
        }
        return opponentFav;
    }

    public boolean ready() {
        return referenceString.length()>windowSize;
    }

    public String getName() {
        return name;
    }
}
