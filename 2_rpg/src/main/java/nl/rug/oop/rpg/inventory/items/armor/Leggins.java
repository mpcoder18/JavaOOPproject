package nl.rug.oop.rpg.inventory.items.armor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class that represents a pair of leggins.
 */
public class Leggins extends ArmorPiece implements Serializable {
    @Serial
    private static final long serialVersionUID = 930359108879L;

    public Leggins(String description, int value, int protection) {
        super("Leggins", description, value, protection);
    }
}
