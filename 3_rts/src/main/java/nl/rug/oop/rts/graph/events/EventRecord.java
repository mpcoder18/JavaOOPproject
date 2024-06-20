package nl.rug.oop.rts.graph.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.util.json.JsonObject;
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

    /**
     * Create a new EventRecord from a JSON object.
     *
     * @param jsonObject - the JSON object
     */
    public EventRecord(JsonObject jsonObject) {
        EventFactory eventFactory = new EventFactory();
        this.event = eventFactory.createEvent(EventType.valueOf((String) jsonObject.get("Event")));
        this.step = Integer.parseInt((String) jsonObject.get("Step"));
    }

    /**
     * Convert the EventRecord to a JsonObject.
     *
     * @return the JsonObject representation of the EventRecord
     */
    public JsonObject toJson() {
        return new JsonObject()
                .put("Event", event.getType().toString())
                .put("Step", step)
                .put("TargetId", target.toJson().get("Id"))
                .put("TargetType", target.toJson().get("Type"));
    }
}
