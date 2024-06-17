package nl.rug.oop.rts.graph.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.rug.oop.rts.JsonObject;
import nl.rug.oop.rts.objects.Army;

/**
 * An event that can be executed on an army.
 */
@AllArgsConstructor
@Getter
public abstract class Event {
    private EventType type;

    public abstract void execute(Army army);

    public String getDescription() {
        return type.getDescription();
    }

    public JsonObject toJson() {
        return new JsonObject()
                .put("Type", type.toString());
    }
}
