package nl.rug.oop.rts.graph.events.types;

import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;

/**
 * A natural disaster event. Removes 35% of the units in the army.
 */
public class NaturalDisasterEvent extends Event {

    public NaturalDisasterEvent() {
        super(EventType.NATURAL_DISASTER);
    }

    @Override
    public void execute(Army army) {
        int numUnits = army.getUnits().size();
        int numUnitsToRemove = (int) Math.ceil(numUnits * 0.35);
        army.removeRandomUnits(numUnitsToRemove);
    }
}
