package nl.rug.oop.rpg.inventory.items;

import nl.rug.oop.rpg.inventory.Item;

/**
 * Class that represents a key item.
 */
public class Key extends Item {
    public Key(String description, int value) {
        super("Key", description, value);
    }
}
