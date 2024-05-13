package nl.rug.oop.rpg.inventory.items.armor;

import lombok.Getter;
import nl.rug.oop.rpg.inventory.Item;

/**
 * Class that represents an armor piece.
 */
@Getter
public class ArmorPiece extends Item {
    private final int defense;

    public ArmorPiece(String name, String description, int value, int defense) {
        super(name, description, value);
        this.defense = defense;
    }
}
