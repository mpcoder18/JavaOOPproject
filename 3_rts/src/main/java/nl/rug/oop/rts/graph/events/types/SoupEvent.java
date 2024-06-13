package nl.rug.oop.rts.graph.events.types;

import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;

/**
 * A soup event. Adds 1 health to all units in the army.
 */
public class SoupEvent extends Event {

    public SoupEvent() {
        super(EventType.SOUP);
    }

    @Override
    public void execute(Army army) {
        army.addHealthToAllUnits(1);
    }
}
