package nl.rug.oop.rts.graph.events.types;

import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Unit;

/**
 * A blessing event. Adds a powerful unit to the army.
 */
public class BlessingEvent extends Event {

    public BlessingEvent() {
        super(EventType.BLESSING);
    }

    @Override
    public void execute(Army army) {
        army.getUnits().add(new Unit(20, 20, "Blessed Unit"));
    }
}
