package nl.rug.oop.rpg.inventory.items;

import nl.rug.oop.rpg.inventory.Item;

public class Sword extends Item {
    private int damage;

    public Sword(String name, String description, int value, int damage) {
        super(name, description, value);
        this.damage = damage;
    }
}
