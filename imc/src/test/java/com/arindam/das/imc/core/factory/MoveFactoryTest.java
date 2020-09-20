package com.arindam.das.imc.core.factory;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.model.Move;
import org.junit.Before;
import org.junit.Test;

public class MoveFactoryTest {
    @Before
    public void start() { }

    @Test
    public void testMoveCreation() {
        Move paper = MoveFactory.create(MoveType.paper);
        assert paper.getMoveType().equals(MoveType.paper);
        assert paper.losesTo().equals(MoveType.scissors);
        assert paper.beats(MoveType.rock);

        Move rock = MoveFactory.create(MoveType.rock);
        assert rock.getMoveType().equals(MoveType.rock);
        assert rock.losesTo().equals(MoveType.paper);
        assert rock.beats(MoveType.scissors);

        Move scissors = MoveFactory.create(MoveType.scissors);
        assert scissors.getMoveType().equals(MoveType.scissors);
        assert scissors.losesTo().equals(MoveType.rock);
        assert scissors.beats(MoveType.paper);
    }
}
