package nl.rug.oop.rts.graph.events.types;

import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;

/**
 * A sabotage event. Adds 2 damage to all units in the army.
 */
public class TrainingEvent extends Event {

    public TrainingEvent() {
        super(EventType.TRAINING);
    }

    @Override
    public void execute(Army army) {
        army.addDamageToAllUnits(2);
    }
}
