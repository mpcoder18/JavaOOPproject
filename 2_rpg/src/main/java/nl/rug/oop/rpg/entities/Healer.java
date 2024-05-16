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
    private boolean healed = false;

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

    @Override
    public void interact(Player player) {
        System.out.println("You greet the healer.");
        if (healed) {
            System.out.println("The healer has already healed you.");
            return;
        }
        System.out.println("The healer heals you for 10 health.");
        player.heal(10);
        healed = true;
    }
}
