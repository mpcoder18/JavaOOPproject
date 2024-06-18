package nl.rug.oop.rts.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.util.List;

/**
 * Abstract class to represent a faction in the game.
 */
@AllArgsConstructor
@Getter
public enum Faction {
    MEN("Men", Team.TEAM_A, List.of("Gondor Soldier", "Tower Guard", "Ithilien Ranger")),
    ELVES("Elves", Team.TEAM_A, List.of("Lorien Warrior", "Mirkwood Archer", "Rivendell Lancer")),
    DWARVES("Dwarves", Team.TEAM_A, List.of("Guardian", "Phalanx", "Axe Thrower")),
    MORDOR("Mordor", Team.TEAM_B, List.of("Orc Warrior", "Orc Pikeman", "Haradrim Archer")),
    ISENGARD("Isengard", Team.TEAM_B, List.of("Uruk-hai", "Uruk Crossbowman", "Warg Rider"));

    private final String name;
    private final Team team;
    private final List<String> unitNames;

    @Override
    public String toString() {
        return name;
    }

    public Color getColor() {
        return team.getColor();
    }
}
