package nl.rug.oop.rpg.entities;

import lombok.Getter;
import nl.rug.oop.rpg.ChoiceMenu;
import nl.rug.oop.rpg.inventory.Item;
import nl.rug.oop.rpg.inventory.items.Key;
import nl.rug.oop.rpg.inventory.items.Sword;
import nl.rug.oop.rpg.inventory.items.armor.Boots;
import nl.rug.oop.rpg.inventory.items.armor.Chestplate;
import nl.rug.oop.rpg.inventory.items.armor.Helmet;
import nl.rug.oop.rpg.inventory.items.armor.Leggings;
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
    private final List<Item> inventory;

    /**
     * Constructor to create a new NPC.
     *
     * @param description Description of the NPC
     * @param damage      Damage the NPC can deal
     * @param health      Health of the NPC
     */
    public Trader(String description, int damage, int health) {
        super(description, damage, health);
        inventory = new ArrayList<>();
        int numberOfItems = (int) (Math.random() * 5) + 1; // Random number of items between 1 and 5
        List<String> itemNames = List.of("Sword", "Key", "Helmet", "Chestplate", "Leggings", "Boots");
        for (int i = 0; i < numberOfItems; i++) {
            String itemName = itemNames.get((int) (Math.random() * itemNames.size()));
            switch (itemName) {
                case "Sword" -> inventory.add(new Sword("Sword", "A sword",
                        (int) (Math.random() * 100) + 1, (int) (Math.random() * 30) + 1));
                case "Key" -> inventory.add(new Key("Key", (int) (Math.random() * 100) + 1));
                case "Helmet" -> inventory.add(new Helmet("Helmet",
                        (int) (Math.random() * 100) + 1, (int) (Math.random() * 30) + 1));
                case "Chestplate" -> inventory.add(new Chestplate("Chestplate",
                        (int) (Math.random() * 100) + 1, (int) (Math.random() * 30) + 1));
                case "Leggings" -> inventory.add(new Leggings("Leggings",
                        (int) (Math.random() * 100) + 1, (int) (Math.random() * 30) + 1));
                case "Boots" -> inventory.add(new Boots("Boots",
                        (int) (Math.random() * 100) + 1, (int) (Math.random() * 30) + 1));
            }
        }
    }

    @Override
    public void interact(Player player) {
        TraderMenu traderMenu = new TraderMenu(this, inventory);
        ChoiceMenu choiceMenu = new ChoiceMenu("What do you want to do?");
        choiceMenu.addChoice(0, () -> traderMenu.buyMenu(player), "Buy");
        choiceMenu.addChoice(1, () -> traderMenu.sellMenu(player), "Sell");
        choiceMenu.run(player.getGame().getScanner());
    }
}
