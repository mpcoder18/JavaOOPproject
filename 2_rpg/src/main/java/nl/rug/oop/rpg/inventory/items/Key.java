package nl.rug.oop.rpg.inventory.items;

import nl.rug.oop.rpg.inventory.Item;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class that represents a key item.
 */
public class Key extends Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 939959107779L;

    public Key(String description, int value) {
        super("Key", description, value);
    }
}
