package nl.rug.oop.rts;

import nl.rug.oop.rts.objects.Faction;

/**
 * A utility class for working with Factions.
 */
public class FactionUtil {
    /**
     * Get a Faction by its name.
     *
     * @param name the name of the Faction
     * @return the Faction
     */
    public Faction getFaction(String name) {
        for (Faction faction : Faction.values()) {
            if (faction.getName().equals(name)) {
                return faction;
            }
        }
        return null;
    }
}
