package nl.rug.oop.rpg.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class to represent an item.
 */
@AllArgsConstructor
@Getter
public abstract class Item {
    private final String name;
    private final String description;
    private final int value;
}
