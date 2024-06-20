package nl.rug.oop.rts.graph;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventFactory;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.util.json.JsonList;
import nl.rug.oop.rts.util.json.JsonObject;

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

        return new JsonObject()
                .put("Id", ID)
                .put("Name", name)
                .put("StartNode", startNode.getID())
                .put("EndNode", endNode.getID())
                .put("Type", "Edge")
                .putList("Armies", armies)
                .putList("Events", events);
    }
}
