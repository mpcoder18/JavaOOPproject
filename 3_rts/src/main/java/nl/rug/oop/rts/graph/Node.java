package nl.rug.oop.rts.graph;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.JsonObject;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.objects.Army;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a node in the graph.
 */
@Getter
public class Node implements Selectable {
    private final int ID;
    @Setter
    private String name;
    private final List<Edge> edgeList;
    @Setter
    private int x;
    @Setter
    private int y;
    @Setter
    private boolean selected;
    private List<Army> armies;
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

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject()
                .put("Id", ID)
                .put("Name", name)
                .put("X", x)
                .put("Y", y);
        List<String> armiesJson = new ArrayList<>();
        for (Army army : armies) {
            armiesJson.add(army.toJson().toJsonString());
        }
        jsonObject.put("Armies", "[" + String.join(",", armiesJson) + "]");

        List<String> eventsJson = new ArrayList<>();
        for (Event event : events) {
            eventsJson.add(event.toJson().toJsonString());
        }
        jsonObject.put("Events", "[" + String.join(",", eventsJson) + "]");

        return jsonObject;
    }
}
