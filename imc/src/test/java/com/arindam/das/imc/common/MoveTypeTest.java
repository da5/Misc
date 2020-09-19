package com.arindam.das.imc.common;

import org.junit.Test;

public class MoveTypeTest {

    @Test
    public void TestValueOfLabel() {
        assert MoveType.scissors.equals(MoveType.valueOfLabel("S"));
        assert MoveType.rock.equals(MoveType.valueOfLabel("R"));
        assert MoveType.paper.equals(MoveType.valueOfLabel("P"));

        assert MoveType.valueOfLabel("X")==null;
    }
}
