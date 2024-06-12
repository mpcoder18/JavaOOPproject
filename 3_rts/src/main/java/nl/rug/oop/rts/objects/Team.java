package nl.rug.oop.rts.objects;

import java.awt.*;

/**
 * The teams to which factions belong.
 */
public enum Team {
    TEAM_A, TEAM_B;

    public Color getColor() {
        return this == TEAM_A ? Color.BLUE : Color.RED;
    }
}
