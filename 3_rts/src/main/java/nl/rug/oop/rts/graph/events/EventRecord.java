package nl.rug.oop.rts.graph.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Used to store the event and the step it was executed in.
 */
@Getter
@AllArgsConstructor
public class EventRecord {
    private final Event event;
    private final int step;
}
