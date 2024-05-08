package nl.rug.oop.rpg.entities;

import lombok.Getter;
import nl.rug.oop.rpg.inventory.Item;
import nl.rug.oop.rpg.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Trader extends NPC {
    @Getter
    private List<Item> inventory = new ArrayList<>();

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

    public void buyItem(Player player, Item item) {
        player.getInventory().remove(item);
        player.setMoney(player.getMoney() + item.getValue());
    }

    public void sellItem(Player player, Item item) {
        inventory.remove(item);
        player.getInventory().add(item);
        player.setMoney(player.getMoney() - item.getValue());
    }
}
