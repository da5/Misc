package com.arindam.das.imc.core.factory;

import com.arindam.das.imc.common.CommandLineInput;
import com.arindam.das.imc.common.GameType;
import com.arindam.das.imc.model.Game;
import com.arindam.das.imc.model.player.Computer;
import com.arindam.das.imc.model.player.User;

public class GameFactory {
    public static Game create(GameType gameType) {
        Game game = null;
        switch (gameType) {
            case vsHuman:
                game = new Game(new User("Player-1", new CommandLineInput()), new User("Player-2", new CommandLineInput()));
                break;
            case vsComputer:
                game = new Game(new User("Player-1", new CommandLineInput()), new Computer());
                break;
            case simulation:
                game = new Game(new Computer(), new Computer());
        }
        return game;
    }
}
