package com.arindam.das.imc.common;

public enum MoveType {
    paper("P"),
    rock("R"),
    scissors("S");

    public final String label;

    private MoveType(String label) {
        this.label = label;
    }

    public static MoveType valueOfLabel(String label) {
        for(MoveType moveType: values()) {
            if(moveType.label.equals(label)) {
                return moveType;
            }
        }
        return null;
    }

    public static String options() {
        return "Please enter a move: Paper(P), Rock(R) or Scissors(S) ::";
    }
}