package nl.rug.oop.rpg;

/**
 * Class to represent a player.
 */
public class Player {
    private String name;
    private Room currentRoom;

    /**
     * Constructor to create a new player.
     * @param name Name of the player
     * @param currentRoom Current room in which the player is standing
     */
    public Player(String name, Room currentRoom) {
        this.name = name;
        this.currentRoom = currentRoom;
    }

    /**
     * Getter method to get the current room.
     * @return The current room object
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
}
