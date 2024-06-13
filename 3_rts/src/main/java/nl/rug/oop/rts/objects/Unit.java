package nl.rug.oop.rts.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A unit is a single entity that can move and attack.
 */
@AllArgsConstructor
@Getter
public class Unit {
    @Setter
    private int damage;
    @Setter
    private int health;
    private String name;

    /**
     * Attack another unit.
     *
     * @param unit - the unit to attack
     */
    public void attack(Unit unit) {
        unit.health -= damage;
    }
}
