package nl.rug.oop.rts.objects;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.JsonList;
import nl.rug.oop.rts.JsonObject;
import nl.rug.oop.rts.graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An army is a group of units.
 */
@Getter
public class Army {
    private final List<Unit> units = new ArrayList<>();
    private final Faction faction;
    private final Team team;
    @Setter
    private Node movingToNextStep;
    @Setter
    private boolean moved = false;

    /**
     * Create a new army.
     *
     * @param units   - the units
     * @param faction - the faction
     */
    public Army(List<Unit> units, Faction faction) {
        this.units.addAll(units);
        this.faction = faction;
        this.team = faction.getTeam();
    }

    /**
     * Populate the army with a given number of units.
     *
     * @param numberOfUnits - the number of units to add
     */
    public void populateArmy(Integer numberOfUnits) {
        Random rand = new Random();

        for (int i = 0; i < numberOfUnits; i++) {
            units.add(new Unit(rand.nextInt(3) + 1, rand.nextInt(3) + 1,
                    faction.getUnitNames().get(rand.nextInt(faction.getUnitNames().size()))));
        }
    }

    /**
     * Remove a given number of units from the army at random.
     *
     * @param numUnitsToRemove - the number of units to remove
     */

    public void removeRandomUnits(Integer numUnitsToRemove) {
        Random rand = new Random();
        for (int i = 0; i < numUnitsToRemove; i++) {
            units.remove(rand.nextInt(units.size()));
        }
    }

    /**
     * Add damage to all units in the army.
     *
     * @param damage - the damage to add
     */
    public void addDamageToAllUnits(Integer damage) {
        for (Unit unit : units) {
            unit.setStrength(Math.max(unit.getStrength() + damage, 0));
        }
    }

    /**
     * Add health to all units in the army.
     *
     * @param health - the health to add
     */
    public void addHealthToAllUnits(Integer health) {
        for (Unit unit : units) {
            unit.setHealth(Math.max(unit.getHealth() + health, 0));
            if (unit.getHealth() == 0) {
                units.remove(unit);
            }
        }
    }

    // TODO: implement an interface for toJson and fromJson ?
    /**
     * Convert the army to a JSON object.
     *
     * @return the JSON object
     */
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject()
                .put("Name", faction.getName())
                .put("Faction", faction.getName())
                .put("Team", team.toString());

        JsonList unitsJsonList = new JsonList(new Object[0]);
        for (Unit unit : units) {
            unitsJsonList.add(unit.toJson());
        }
        jsonObject.put("Units", unitsJsonList);

        return jsonObject;
    }
}
