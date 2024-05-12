package nl.rug.oop.rpg.player;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rpg.inventory.Item;
import nl.rug.oop.rpg.interfaces.Attackable;
import nl.rug.oop.rpg.entities.Enemy;
import nl.rug.oop.rpg.environment.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a player.
 */
public class Player implements Attackable {
    private final String name;
    @Setter
    @Getter
    private Room currentRoom;
    /**
     * Health of the player.
     */
    @Setter
    @Getter
    protected int health;
    /**
     * Damage that the player can deal.
     */
    @Getter
    @Setter
    protected int damage;

    @Getter
    @Setter
    private int money;

    @Getter
    @Setter
    private List<Item> inventory;

    /**
     * Constructor to create a new player.
     *
     * @param name        Name of the player
     * @param currentRoom Current room in which the player is standing
     * @param health      Health of the player
     * @param damage      Damage that the player can deal
     */
    public Player(String name, Room currentRoom, int health, int damage) {
        this.name = name;
        this.currentRoom = currentRoom;
        this.health = health;
        this.damage = damage;
        this.money = 0;
        this.inventory = new ArrayList<>();
    }

    /**
     * Method to attack an attackable entity.
     *
     * @param attackable entity to attack (Casted to Enemy)
     */
    public void attack(Attackable attackable) {
        Enemy enemy = (Enemy) attackable;
        enemy.setHealth(enemy.getHealth() - this.damage);
        System.out.println("You attack the enemy and deal " + this.damage + " damage.");
        System.out.println("The enemy has " + enemy.getHealth() + " health left.");
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }
}
