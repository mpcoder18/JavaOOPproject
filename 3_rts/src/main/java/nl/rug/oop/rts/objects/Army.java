package nl.rug.oop.rts.objects;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.graph.Node;

import java.util.ArrayList;
import java.util.List;

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
}
