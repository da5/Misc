package com.arindam.das.imc.common;

public enum GameType {
    vsHuman("H"),
    vsComputer("C"),
    simulation("S");

    public final String label;

    private GameType(String label) {
        this.label = label;
    }

    public static GameType valueOfLabel(String label) {
        for(GameType gameType: values()) {
            if(gameType.label.equals(label)) {
                return gameType;
            }
        }
        return null;
    }

    public static String options() {
        return "Please enter an opponent: Human(H), Computer(C), Simulation(S) or anything else to EXIT ::";
    }
}
