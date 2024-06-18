package nl.rug.oop.rts;

import nl.rug.oop.rts.objects.Faction;

public class FactionUtil {
    public Faction getFaction(String name) {
        for (Faction faction : Faction.values()) {
            if (faction.getName().equals(name)) {
                return faction;
            }
        }
        return null;
    }
}
