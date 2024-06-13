package nl.rug.oop.rts.graph.events.types;

import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;

/**
 * A sabotage event. Removes 2 damage from all units in the army.
 */
public class SabotageEvent extends Event {

    public SabotageEvent() {
        super(EventType.SABOTAGE);
    }

    @Override
    public void execute(Army army) {
        army.addDamageToAllUnits(-2);
    }
}
