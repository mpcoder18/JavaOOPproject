package nl.rug.oop.rpg.entities;

import nl.rug.oop.rpg.player.Player;

public class Wizard extends NPC {
    /**
     * Constructor to create a new NPC.
     *
     * @param description Description of the NPC
     * @param damage      Damage the NPC can deal
     * @param health      Health of the NPC
     */
    public Wizard(String description, int damage, int health) {
        super(description, damage, health);
    }

    public void buff(Player player, int damage) {
        player.setDamage(player.getDamage() + damage);
    }
}
