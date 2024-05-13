package nl.rug.oop.rpg.entities;

import nl.rug.oop.rpg.inventory.items.Sword;
import nl.rug.oop.rpg.player.Player;

import java.util.Random;

/**
 * Class that represents a wizard NPC.
 */
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

    public void enchantItem(Player player, Sword sword) {
        if (player.getMoney() < 10) {
            System.out.println("You do not have enough money to enchant your sword.");
            return;
        }
        Random random = new Random();
        int damage = random.nextInt(5) + 1;
        sword.setDamage(sword.getDamage() + damage);
        player.setMoney(player.getMoney() - 10);
        System.out.println("You have enchanted your sword with " + damage + " damage.");
    }
}
