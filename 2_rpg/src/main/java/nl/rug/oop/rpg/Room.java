package nl.rug.oop.rpg;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a room.
 */
public class Room implements Inspectable {
    private String description;
    private List<Door> doors;

    /**
     * Constructor to create a new room.
     * @param description Description of the new room
     */
    public Room(String description) {
        this.description = description;
        this.doors = new ArrayList<>();
    }

    /**
     * Method to inspect a room (outputs the description of the room).
     */
    public void inspect() {
        System.out.println("You see: " + description);
    }

    /**
     * Method to add a door to a room.
     * @param door Door to add to the room
     */
    public void addDoor(Door door) {
        doors.add(door);
    }
}
