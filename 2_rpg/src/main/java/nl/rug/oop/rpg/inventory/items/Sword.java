package nl.rug.oop.rpg.inventory.items;

import nl.rug.oop.rpg.inventory.Item;

/**
 * Class that represents a sword item.
 */
public class Sword extends Item {
    private int damage;

    /**
     * Constructor to create a new sword.
     *
     * @param name        Name of the sword
     * @param description Description of the sword
     * @param value       Value of the sword in gold
     * @param damage      Damage the sword can deal
     */
    public Sword(String name, String description, int value, int damage) {
        super(name, description, value);
        this.damage = damage;
    }
}
