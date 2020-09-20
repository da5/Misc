package com.arindam.das.imc.model.player;

import com.arindam.das.imc.common.CommandLineInput;
import com.arindam.das.imc.common.MoveType;
import com.arindam.das.imc.model.Player;

public class User extends Player {
    private CommandLineInput commandLineInput;

    public User(String name, CommandLineInput commandLineInput) {
        super(name);
        this.commandLineInput = commandLineInput;
    }

    public MoveType move() {
        System.out.print(getName() + " input : ");
        String inputMove = commandLineInput.getUserInput();
        return MoveType.valueOfLabel(inputMove.toUpperCase());
    }

    public void consumeOpponentInput(MoveType moveType, int reward) {
        //do nothing
    }
}
