package nl.rug.oop.rts.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.JsonObject;

/**
 * A unit is a single entity that can move and attack.
 */
@AllArgsConstructor
@Getter
public class Unit {
    @Setter
    private int strength;
    @Setter
    private int health;
    private String name;

    /**
     * Create a new unit from a JSON object.
     *
     * @param jsonObject - the JSON object
     */
    public Unit(JsonObject jsonObject) {
        this.strength = (int) jsonObject.get("Strength");
        this.health = (int) jsonObject.get("Health");
        this.name = (String) jsonObject.get("Name");
    }

    /**
     * Attack another unit.
     *
     * @param unit - the unit to attack
     */
    public void attack(Unit unit) {
        unit.health -= strength;
    }

    /**
     * Convert the unit to a JsonObject.
     *
     * @return the JsonObject representation of the unit
     */
    public JsonObject toJson() {
        return new JsonObject()
                .put("Name", name)
                .put("Strength", strength)
                .put("Health", health);
    }
}
