package nl.rug.oop.rts.graph.events.types;

import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;

/**
 * A black plague event that removes 50% of the units in the army.
 */
public class BlackPlagueEvent extends Event {

    public BlackPlagueEvent() {
        super(EventType.BLACK_PLAGUE);
    }

    @Override
    public void execute(Army army) {
        int numUnits = army.getUnits().size();
        int numUnitsToRemove = (int) Math.ceil(numUnits * 0.5);
        army.removeRandomUnits(numUnitsToRemove);
    }
}
