package nl.rug.oop.rts.graph.events.types;

import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;

/**
 * A natural disaster event that can be executed on an army.
 */
public class ReinforcementsEvent extends Event {

    public ReinforcementsEvent() {
        super(EventType.REINFORCEMENTS);
    }

    @Override
    public void execute(Army army) {
        army.populateArmy(15);
    }
}
