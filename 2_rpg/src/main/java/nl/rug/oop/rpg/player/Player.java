package nl.rug.oop.rpg.player;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rpg.inventory.Item;
import nl.rug.oop.rpg.interfaces.Attackable;
import nl.rug.oop.rpg.entities.Enemy;
import nl.rug.oop.rpg.environment.Room;
import nl.rug.oop.rpg.inventory.items.Armor;
import nl.rug.oop.rpg.inventory.items.Sword;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a player.
 */
@Setter
@Getter
public class Player implements Attackable, Serializable {
    @Serial
    private static final long serialVersionUID = 930359107779L;
    private final String name;
    private Room currentRoom;
    /**
     * Health of the player.
     */
    protected int health;
    /**
     * Sword of the player.
     */
    protected Sword sword;
    /**
     * Armor of the player.
     */
    protected Armor armor;
    private int money;
    private List<Item> inventory;

    /**
     * Constructor to create a new player.
     *
     * @param name        Name of the player
     * @param currentRoom Current room in which the player is standing
     * @param health      Health of the player
     */
    public Player(String name, Room currentRoom, int health) {
        this.name = name;
        this.currentRoom = currentRoom;
        this.health = health;
        this.money = 0;
        this.sword = new Sword("Fists", "Your fists", -1, 1);
        this.armor = new Armor();
        this.inventory = new ArrayList<>();
    }

    /**
     * Method to attack an attackable entity.
     *
     * @param attackable entity to attack (Casted to Enemy)
     */
    public void attack(Attackable attackable) {
        Enemy enemy = (Enemy) attackable;
        enemy.receiveDamage(this.sword.getDamage());
    }

    public void receiveDamage(int damage) {
        if (this.armor.getDefense() >= damage) {
            System.out.println("You receive no damage.");
            return;
        }
        damage -= this.armor.getDefense();
        this.health -= damage;
        System.out.println("You receive " + damage + " damage.");
        System.out.println("You have " + this.health + " health left.");
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public boolean hasItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }
}
