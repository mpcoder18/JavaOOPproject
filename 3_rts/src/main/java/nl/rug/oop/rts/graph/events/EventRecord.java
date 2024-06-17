package nl.rug.oop.rts.graph.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.rug.oop.rts.graph.Selectable;

/**
 * Used to store the event and the step it was executed in.
 */
@Getter
@AllArgsConstructor
public class EventRecord {
    private final Event event;
    private Selectable target;
    private final int step;
}