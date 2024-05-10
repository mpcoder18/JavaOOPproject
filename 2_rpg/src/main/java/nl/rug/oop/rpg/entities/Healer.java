package nl.rug.oop.rpg.entities;

import nl.rug.oop.rpg.player.Player;

/**
 * Class that represents a healer NPC.
 */
public class Healer extends NPC {
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

    public void giveHealth(Player player, int health){
        player.setHealth(player.getHealth() + health);
    }
}
