package com.arindam.das.imc.core.manager;

import com.arindam.das.imc.common.CommandLineInput;
import com.arindam.das.imc.common.GameType;
import com.arindam.das.imc.common.MenuType;
import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.factory.GameFactory;
import com.arindam.das.imc.model.Game;
import com.google.common.annotations.VisibleForTesting;

public class Moderator {
    private boolean power;
    private MenuType menuType;
    private CommandLineInput commandLineInput;

    private Moderator(CommandLineInput commandLineInput) {
        this.power = true;
        this.menuType = MenuType.GameMenu;
        this.commandLineInput = commandLineInput;
    }

    private static class BillPughSingleton {
        private static final Moderator instance = new Moderator(new CommandLineInput());
    }

    public static Moderator getInstance() {
        return Moderator.BillPughSingleton.instance;
    }

    public void run() {
        Game game = null;
        while (power) {
            switch (menuType) {
                case GameMenu:
                    GameType gameType = gameMenu();
                    if(gameType!=null) {
                        game = GameFactory.create(gameType);
                    }
                    break;
                case MoveMenu:
                    moveMenu(game);
                    if(menuType == MenuType.GameMenu) {
                        System.out.println("Game concluded : " + game.getScore());
                    }
                    break;
                case ExitMenu:
                    exitMenu();
                    break;
            }
            System.out.println("---------------------------------------------");
        }
    }

    @VisibleForTesting
    protected void exitMenu() {
        power = false;
    }

    @VisibleForTesting
    protected GameType gameMenu() {
        System.out.println("Select a game type ... " + GameType.options());
        String input = commandLineInput.getUserInput();
        GameType gameType = GameType.valueOfLabel(input.toUpperCase());
        if(gameType==null) {
            menuType = MenuType.ExitMenu;
        } else {
            menuType = MenuType.MoveMenu;
        }
        return gameType;
    }

    @VisibleForTesting
    protected void moveMenu(Game game) {
        System.out.println("Select a move type ... " + MoveType.options());
        Game.GameMove gameMove = game.performMove();
        if(gameMove==null) {
            menuType = MenuType.GameMenu;
        } else {
            System.out.println(gameMove.toString());
        }
    }

    @VisibleForTesting
    protected boolean isPower() {
        return power;
    }

    @VisibleForTesting
    protected MenuType getMenuType() {
        return menuType;
    }

    @VisibleForTesting
    protected void setCommandLineInput(CommandLineInput commandLineInput) {
        this.commandLineInput = commandLineInput;
    }
}
