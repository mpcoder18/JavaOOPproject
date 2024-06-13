package nl.rug.oop.rts.graph.events;

import lombok.Getter;
import nl.rug.oop.rts.objects.Army;
import lombok.AllArgsConstructor;

/**
 * An event that can be executed on an army.
 */
@AllArgsConstructor
@Getter
public abstract class Event {
    private EventType type;

    public abstract void execute(Army army);
}
