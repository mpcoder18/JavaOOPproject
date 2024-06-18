package nl.rug.oop.rts.graph.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.JsonObject;
import nl.rug.oop.rts.graph.Selectable;

/**
 * Used to store the event and the step it was executed in.
 */
@Getter
@AllArgsConstructor
public class EventRecord {
    private final Event event;
    @Setter
    private Selectable target;
    private final int step;

    public JsonObject toJson() {
        return new JsonObject()
                .put("Event", event.getType().toString())
                .put("Step", step)
                .put("TargetId", target.toJson().get("Id"))
                .put("TargetType", target.toJson().get("Type"));
    }

    public EventRecord(JsonObject jsonObject) {
        EventFactory eventFactory = new EventFactory();
        this.event = eventFactory.createEvent(EventType.valueOf((String) jsonObject.get("Event")));
        this.step = Integer.parseInt((String) jsonObject.get("Step"));
    }
}
