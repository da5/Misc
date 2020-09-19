package com.arindam.das.imc.core.factory;

import com.arindam.das.imc.common.GameType;
import com.arindam.das.imc.model.Game;
import com.arindam.das.imc.model.player.Computer;
import com.arindam.das.imc.model.player.User;

public class GameFactory {
    public static Game create(GameType gameType) {
        Game game = null;
        switch (gameType) {
            case vsHuman:
                game = new Game(new User("Player-1"), new User("Player-2"));
                break;
            case vsComputer:
                game = new Game(new User("Player-1"), new Computer());
                break;
        }
        return game;
    }
}
