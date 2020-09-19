package com.arindam.das.imc.core.assets.strategies;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.exceptions.StrategyNotReadyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FrequencyCountingStrategyTest {
    private FrequencyCounting frequencyCounting;

    @Before
    public void start() {
        frequencyCounting = new FrequencyCounting();
    }

    @Test(expected = StrategyNotReadyException.class)
    public void TestNotReady() {
        assert !frequencyCounting.ready();
        frequencyCounting.consume(MoveType.scissors);
        assert !frequencyCounting.ready();
        frequencyCounting.suggestOpponentMove();
    }

    @Test
    public void TestFrequencyCounting() {
        frequencyCounting.customizeWindowLengths(3,3);
        assert frequencyCounting.getName().equals("FrequencyCounting");

        assert !frequencyCounting.ready();
        frequencyCounting.consume(MoveType.rock);
        assert !frequencyCounting.ready();
        frequencyCounting.consume(MoveType.rock);
        assert !frequencyCounting.ready();
        frequencyCounting.consume(MoveType.scissors);

        assert frequencyCounting.ready();
        assert frequencyCounting.suggestOpponentMove()==MoveType.rock;

        frequencyCounting.consume(MoveType.paper);
        assert frequencyCounting.suggestOpponentMove()==null;

        frequencyCounting.consume(MoveType.paper);
        assert frequencyCounting.suggestOpponentMove()==MoveType.paper;

        frequencyCounting.consume(MoveType.rock);
        assert frequencyCounting.suggestOpponentMove()==MoveType.paper;
    }

    @After
    public void tearDown(){
    }
}
