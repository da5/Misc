package com.arindam.das.imc.core.manager;

import com.arindam.das.imc.common.GameType;
import com.arindam.das.imc.common.MenuType;
import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.core.factory.GameFactory;
import com.arindam.das.imc.model.Game;

import java.util.Scanner;

public class Moderator {
    private Scanner scanner;
    private boolean power;
    private MenuType menuType;

    private Moderator() {
        power = true;
        menuType = MenuType.GameMenu;
        scanner = new Scanner(System.in);
    }

    private static class BillPughSingleton {
        private static final Moderator instance = new Moderator();
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
                    power = false;
                    break;
            }
            System.out.println("---------------------------------------------");
        }
    }

    private GameType gameMenu() {
        System.out.println("Select a game type ... " + GameType.options());
        String input = scanner.next();
        GameType gameType = GameType.valueOfLabel(input.toUpperCase());
        if(gameType==null) {
            menuType = MenuType.ExitMenu;
        } else {
            menuType = MenuType.MoveMenu;
        }
        return gameType;
    }

    private void moveMenu(Game game) {
        System.out.println("Select a move type ... " + MoveType.options());
        Game.GameMove gameMove = game.performMove();
        if(gameMove==null) {
            menuType = MenuType.GameMenu;
        } else {
            System.out.println(gameMove.toString());
        }
    }
}
