package com.arindam.das.imc.common;

import org.junit.Test;

public class GameTypeTest {
    @Test
    public void TestValueOfLabel() {
        assert GameType.vsHuman.equals(GameType.valueOfLabel("H"));
        assert GameType.vsComputer.equals(GameType.valueOfLabel("C"));

        assert GameType.valueOfLabel("X")==null;
    }
}
