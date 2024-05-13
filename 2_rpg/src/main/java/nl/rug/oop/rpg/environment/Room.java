package nl.rug.oop.rpg.environment;

import lombok.Getter;
import nl.rug.oop.rpg.interfaces.Inspectable;
import nl.rug.oop.rpg.entities.NPC;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a room.
 */
public class Room implements Inspectable, Serializable {
    @Serial
    private static final long serialVersionUID = 930351107779L;
    @Getter
    private final String description;
    /**
     * -- GETTER --
     * Getter methopd to get the list of doors in the room.
     *
     * @return the list of doors int the room
     */
    @Getter
    private List<Door> doors;
    @Getter
    private List<NPC> npcs;

    /**
     * Constructor to create a new room.
     *
     * @param description Description of the new room
     */
    public Room(String description) {
        this.description = description;
        this.doors = new ArrayList<>();
        this.npcs = new ArrayList<>();
    }

    /**
     * Method to inspect a room (outputs the description of the room).
     */
    public void inspect() {
        System.out.println("You see: " + description);
    }

    /**
     * Method to add a door to a room.
     *
     * @param door Door to add to the room
     */
    public void addDoor(Door door) {
        if (!doors.contains(door)) {
            doors.add(door);
            if (door.getConnectingRoom() != null) {
                door.getConnectingRoom().addDoor(door);
            }
        }
    }
    public void addNPC(NPC npc) {
        npcs.add(npc);
    }
}
