package nl.rug.oop.rpg.inventory;

import lombok.Getter;

/**
 * Class to represent an item.
 */
@Getter
public abstract class Item {
    private final String name;
    private final String description;
    private final int value;

    /**
     * Constructor to create a new item.
     * @param name Name of the item
     * @param description Description of the item
     * @param value Value of the item in gold
     */
    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }
}
