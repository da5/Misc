package com.arindam.das.imc.common;

/*
    This enum type defines order of power of every move in a circular chain. In other words, every move beats or gets
    beaten by equal number of moves on either side. Like scissors would beat paper and get beaten by rock.

    The abstract class Move takes care of the order in which the enums are defined and configures for its derived classes.

    If we want to add more weapons viz. lizard and spock, we could have them in the order:
    spock, rock, scissors, lizard, paper
 */
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
        return "Please enter a move: Paper(P), Rock(R), Scissors(S) or anything else to EXIT ::";
    }
}