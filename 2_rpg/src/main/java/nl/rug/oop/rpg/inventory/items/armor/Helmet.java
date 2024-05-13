package nl.rug.oop.rpg.inventory.items.armor;

import java.io.Serial;
import java.io.Serializable;

public class Helmet extends ArmorPiece implements Serializable {
    @Serial
    private static final long serialVersionUID = 9303591073249L;
    public Helmet(String description, int value, int protection) {
        super("Helmet", description, value, protection);
    }
}
