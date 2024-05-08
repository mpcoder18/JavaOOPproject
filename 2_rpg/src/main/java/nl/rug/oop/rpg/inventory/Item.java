package nl.rug.oop.rpg.inventory;

import lombok.Getter;

@Getter
abstract public class Item {
    private final String name;
    private final String description;
    private final int value;

    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }
}
