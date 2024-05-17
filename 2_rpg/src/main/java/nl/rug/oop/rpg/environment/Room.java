package nl.rug.oop.rpg.environment;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rpg.entities.NPC;
import nl.rug.oop.rpg.interfaces.Inspectable;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a room.
 */
@Getter
public class Room implements Inspectable, Serializable {
    @Serial
    private static final long serialVersionUID = 930351107779L;
    @Setter
    private String description;
    private final List<Door> doors;
    private final List<NPC> npcs;

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
     * Constructor to create a new empty room.
     */
    public Room() {
        this.description = "Empty room";
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
     * @param addBackDoor True if the door should be added to the connecting room as well, false otherwise
     */
    public void addDoor(Door door, boolean addBackDoor) {
        if (!doors.contains(door)) {
            doors.add(door);
            if (addBackDoor) {
                door.getConnectingRoom().addDoor(new Door(door.getDescription(), this), false);
            }
        }
    }

    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    public void shuffleDoors() {
        java.util.Collections.shuffle(doors);
    }
}
