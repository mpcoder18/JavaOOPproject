package nl.rug.oop.rts.graph.events.types;

import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;

/**
 * A hidden weaponry event. Adds 1 damage to all units in the army.
 */
public class HiddenWeaponryEvent extends Event {

    public HiddenWeaponryEvent() {
        super(EventType.HIDDEN_WEAPONRY);
    }

    @Override
    public void execute(Army army) {
        army.addDamageToAllUnits(1);
    }
}
