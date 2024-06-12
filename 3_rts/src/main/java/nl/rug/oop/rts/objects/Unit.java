package nl.rug.oop.rts.objects;

import lombok.AllArgsConstructor;

/**
 * A unit is a single entity that can move and attack.
 */
@AllArgsConstructor
public class Unit {
    private int damage;
    private int health;
    private String name;
}
