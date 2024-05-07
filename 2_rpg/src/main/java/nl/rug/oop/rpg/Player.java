package nl.rug.oop.rpg;

/**
 * Class to represent a player.
 */
public class Player {
    private String name;
    private Room currentRoom;
    /**
     * Health of the player.
     */
    protected int health;
    /**
     * Damage that the player can deal.
     */
    protected int damage;

    /**
     * Constructor to create a new player.
     * @param name Name of the player
     * @param currentRoom Current room in which the player is standing
     * @param health Health of the player
     * @param damage Damage that the player can deal
     */
    public Player(String name, Room currentRoom, int health, int damage) {
        this.name = name;
        this.currentRoom = currentRoom;
        this.health = health;
        this.damage = damage;
    }

    /**
     * Getter method to get the current room.
     * @return The current room object
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void attack(Enemy enemy) {
        enemy.health -= this.damage;
    }

    public int getHealth() {
        return health;
    }
}
