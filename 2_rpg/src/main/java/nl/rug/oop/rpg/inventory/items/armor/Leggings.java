package nl.rug.oop.rpg.inventory.items.armor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class that represents a pair of leggings.
 */
public class Leggings extends ArmorPiece implements Serializable {
    @Serial
    private static final long serialVersionUID = 930359108879L;

    public Leggings(String description, int value, int protection) {
        super("Leggings", description, value, protection);
    }
}
