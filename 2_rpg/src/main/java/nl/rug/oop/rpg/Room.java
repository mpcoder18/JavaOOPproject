package nl.rug.oop.rpg;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a room.
 */
public class Room implements Inspectable {
    private String description;
    private List<Door> doors;
    private List<NPC> npcs;

    /**
     * Constructor to create a new room.
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
     * @param door Door to add to the room
     */
    public void addDoor(Door door) {
        doors.add(door);
    }

    /**
     * Getter methopd to get the list of doors in the room.
     * @return the list of doors int the room
     */
    public List<Door> getDoors() {
        return doors;
    }

    /**
     * Method to list the doors in the room.
     */
    public void lookForWayOut() {
        System.out.println("You look around for doors. You see:");
        for (int i = 0; i < doors.size(); i++) {
            System.out.print("  (" + i + ") ");
            doors.get(i).inspect();
        }
    }

    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    /**
     * Method to list the NPCs in the room.
     */
    public void listCompany() {
        System.out.println("You look if there’s someone here.");
        System.out.println("You see: ");
        for (int i = 0; i < npcs.size(); i++) {
            System.out.print("  (" + i + ") ");
            npcs.get(i).inspect();
        }
    }
}
