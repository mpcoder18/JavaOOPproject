package nl.rug.oop.rpg.inventory.items.armor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class that represents a pair of boots.
 */
public class Boots extends ArmorPiece implements Serializable {
    @Serial
    private static final long serialVersionUID = 938659107779L;

    public Boots(String description, int value, int protection) {
        super("Boots", description, value, protection);
    }
}
