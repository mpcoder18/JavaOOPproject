package nl.rug.oop.rts.graph;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.JsonList;
import nl.rug.oop.rts.JsonObject;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventFactory;
import nl.rug.oop.rts.graph.events.EventRecord;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a node in the graph.
 */
@Getter
public class Node implements Selectable {
    private final int ID;
    private final List<Edge> edgeList;
    @Setter
    private String name;
    @Setter
    private int x;
    @Setter
    private int y;
    @Setter
    private boolean selected;
    private final List<Army> armies;
    @Setter
    private List<Event> events;

    /**
     * Create a new node.
     *
     * @param id   ID of the node
     * @param name name of the node
     * @param x    x coordinate of the node
     * @param y    y coordinate of the node
     */
    public Node(int id, String name, int x, int y) {
        this.ID = id;
        this.name = name;
        this.edgeList = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.selected = false;
        this.armies = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }

    public void removeEdge(Edge edge) {
        edgeList.remove(edge);
    }

    /**
     * Convert the node to a JsonObject.
     *
     * @return JsonObject representation of the node
     */
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject()
                .put("Id", ID)
                .put("Name", name)
                .put("X", x)
                .put("Y", y)
                .put("Type", "Node");
        JsonList armiesJsonList = new JsonList(new Object[0]);
        for (Army army : armies) {
            armiesJsonList.add(army.toJson());
        }
        jsonObject.put("Armies", armiesJsonList);

        JsonList eventsJsonList = new JsonList(new Object[0]);
        for (Event event : events) {
            eventsJsonList.add(event.toJson());
        }
        jsonObject.put("Events", eventsJsonList);

        return jsonObject;
    }

    public Node(JsonObject jsonObject) {
        this.ID = (int) jsonObject.get("Id");
        this.name = (String) jsonObject.get("Name");
        this.x = (int) jsonObject.get("X");
        this.y = (int) jsonObject.get("Y");
        this.edgeList = new ArrayList<>();
        this.selected = false;
        this.armies = new ArrayList<>();
        this.events = new ArrayList<>();
        JsonList armiesJsonList = jsonObject.getList("Armies");
        if (armiesJsonList != null) {
            for (Object armyObject : armiesJsonList.getValues()) {
                armies.add(new Army((JsonObject) armyObject));
            }
        }
        JsonList eventsJsonList = jsonObject.getList("Events");
        EventFactory eventFactory = new EventFactory();
        if (eventsJsonList != null) {
            for (Object eventObject : eventsJsonList.getValues()) {
                events.add(eventFactory.createEvent(EventType.valueOf((String) ((JsonObject) eventObject).get("Type"))));
            }
        }
    }
}
