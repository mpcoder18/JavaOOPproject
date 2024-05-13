package nl.rug.oop.rpg.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class to represent an item.
 */
@AllArgsConstructor
@Getter
public abstract class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 930359107733L;
    private final String name;
    private final String description;
    private final int value;
}
