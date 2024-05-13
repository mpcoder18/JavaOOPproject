package nl.rug.oop.rpg.inventory.items.armor;

import java.io.Serial;
import java.io.Serializable;

public class Chestplate extends ArmorPiece implements Serializable {
    @Serial
    private static final long serialVersionUID = 936559107779L;
    public Chestplate(String description, int value, int protection) {
        super("Chestplate", description, value, protection);
    }
}
