package com.arindam.das.imc.model.player;

import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.model.Player;

import java.util.Scanner;

public class User extends Player {
    private Scanner scanner;

    public User(String name) {
        super(name);
        scanner = new Scanner(System.in);
    }

    public MoveType move() {
        System.out.print(getName() + " input : ");
        String inputMove = scanner.next();
        return MoveType.valueOfLabel(inputMove.toUpperCase());
    }

    public void consumeOpponentInput(MoveType moveType, boolean win) {
        //do nothing
    }
}
