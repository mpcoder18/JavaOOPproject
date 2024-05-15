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
    private List<Door> doors;
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

    /**
     * Method to check if a room is connected to another room.
     *
     * @param otherRoom Room to check if it is connected to
     * @return True if the room is connected to the other room, false otherwise
     */
    public boolean isConnectedTo(Room otherRoom) {
        for (Door door : doors) {
            if (door.getConnectingRoom() == otherRoom) {
                return true;
            }
        }
        return false;
    }
}
