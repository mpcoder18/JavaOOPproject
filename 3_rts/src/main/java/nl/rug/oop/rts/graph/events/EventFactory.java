package nl.rug.oop.rts.graph.events;

import nl.rug.oop.rts.graph.events.types.*;
import nl.rug.oop.rts.objects.Army;

/**
 * A factory for creating events.
 */
public class EventFactory {
    /**
     * Create an event.
     *
     * @param type the type of event
     * @param army the army
     * @return the event
     */
    public Event createEvent(EventType type, Army army) {
        return switch (type) {
            case REINFORCEMENTS -> new ReinforcementsEvent();
            case NATURAL_DISASTER -> new NaturalDisasterEvent();
            case HIDDEN_WEAPONRY -> new HiddenWeaponryEvent();
            case BLACK_PLAGUE -> new BlackPlagueEvent();
            case BLESSING -> new BlessingEvent();
            case SABOTAGE -> new SabotageEvent();
            case TRAINING -> new TrainingEvent();
            case SOUP -> new SoupEvent();
            default -> null;
        };
    }
}
