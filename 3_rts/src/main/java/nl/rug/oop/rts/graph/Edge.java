package nl.rug.oop.rts.graph;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.JsonList;
import nl.rug.oop.rts.JsonObject;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventFactory;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent an edge in the graph.
 */
@Getter
public class Edge implements Selectable {
    private final int ID;
    @Setter
    private Node startNode;
    @Setter
    private Node endNode;
    @Setter
    private String name;
    @Setter
    private boolean selected;
    @Setter
    private List<Army> armies;
    @Setter
    private List<Event> events;

    /**
     * Create a new edge.
     *
     * @param ID        ID of the edge
     * @param name      name of the edge
     * @param startNode start node of the edge
     * @param endNode   end node of the edge
     */
    public Edge(int ID, String name, Node startNode, Node endNode) {
        this.ID = ID;
        this.name = name;
        this.startNode = startNode;
        this.endNode = endNode;
        this.selected = false;
        this.armies = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    /**
     * Create a new edge from a JSON object.
     *
     * @param jsonObject the JSON object
     */
    public Edge(JsonObject jsonObject) {
        this.ID = (int) jsonObject.get("Id");
        this.name = (String) jsonObject.get("Name");
        this.startNode = null;
        this.endNode = null;
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
                EventType eventType = EventType.valueOf((String) ((JsonObject) eventObject).get("Type"));
                events.add(eventFactory.createEvent(eventType));
            }
        }
    }

    /**
     * Get the other node connected to this edge.
     *
     * @param node the current node
     * @return the other node
     */
    public Node getOtherNode(Node node) {
        if (node.equals(startNode)) {
            return endNode;
        } else if (node.equals(endNode)) {
            return startNode;
        } else {
            return null;
        }
    }

    /**
     * Convert the edge to a JsonObject.
     *
     * @return the JsonObject representation of the edge
     */
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("Id", ID);
        jsonObject.put("Name", name);
        jsonObject.put("StartNode", startNode.getID());
        jsonObject.put("EndNode", endNode.getID());
        jsonObject.put("Type", "Edge");
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
}
