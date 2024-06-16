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
     * Attack another unit.
     *
     * @param unit - the unit to attack
     */
    public void attack(Unit unit) {
        unit.health -= strength;
    }

    public JsonObject toJsonObject() {
        return new JsonObject()
                .put("Name", name)
                .put("Strength", strength)
                .put("Health", health);
    }

    public Unit fromJsonObject(JsonObject jsonObject) {
        JsonObject jsonObjectItem = (JsonObject) jsonObject.get("unit");
        return new Unit((int) jsonObjectItem.get("damage"), (int) jsonObjectItem.get("health"), (String) jsonObjectItem.get("name"));
    }
}
