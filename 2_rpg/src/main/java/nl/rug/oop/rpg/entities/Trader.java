package nl.rug.oop.rpg.entities;

import lombok.Getter;
import nl.rug.oop.rpg.inventory.Item;
import nl.rug.oop.rpg.player.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a trader.
 */
@Getter
public class Trader extends NPC implements Serializable {
    @Serial
    private static final long serialVersionUID = 9303594457779L;
    private final List<Item> inventory = new ArrayList<>();

    /**
     * Constructor to create a new NPC.
     *
     * @param description Description of the NPC
     * @param damage      Damage the NPC can deal
     * @param health      Health of the NPC
     */
    public Trader(String description, int damage, int health) {
        super(description, damage, health);
    }

    /**
     * Method to buy an item from the player.
     *
     * @param player Player that sells the item
     * @param item   Item that the player sells
     */
    public void buyItem(Player player, Item item) {
        player.getInventory().remove(item);
        player.setMoney(player.getMoney() + item.getValue());
    }

    /**
     * Method to sell an item to the player.
     *
     * @param player Player that buys the item
     * @param item   Item that the player buys
     */
    public void sellItem(Player player, Item item) {
        inventory.remove(item);
        player.getInventory().add(item);
        player.setMoney(player.getMoney() - item.getValue());
    }
}
