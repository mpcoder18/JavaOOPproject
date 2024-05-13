package nl.rug.oop.rpg.entities;

import nl.rug.oop.rpg.player.Player;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class that represents a healer NPC.
 */
public class Healer extends NPC implements Serializable {
    @Serial
    private static final long serialVersionUID = 930359103379L;
    /**
     * Constructor to create a new NPC.
     *
     * @param description Description of the NPC
     * @param damage      Damage the NPC can deal
     * @param health      Health of the NPC
     */
    public Healer(String description, int damage, int health) {
        super(description, damage, health);
    }

    public void giveHealth(Player player, int health) {
        player.setHealth(player.getHealth() + health);
    }
}
