package nl.rug.oop.rpg.player;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rpg.entities.Enemy;
import nl.rug.oop.rpg.environment.Room;
import nl.rug.oop.rpg.game.Game;
import nl.rug.oop.rpg.interfaces.Attackable;
import nl.rug.oop.rpg.inventory.Item;
import nl.rug.oop.rpg.inventory.items.Armor;
import nl.rug.oop.rpg.inventory.items.Sword;
import nl.rug.oop.rpg.inventory.items.armor.Boots;
import nl.rug.oop.rpg.inventory.items.armor.Chestplate;
import nl.rug.oop.rpg.inventory.items.armor.Helmet;
import nl.rug.oop.rpg.inventory.items.armor.Leggings;

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
    private Game game;

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

    /**
     * Method to receive damage.
     *
     * @param damage amount of damage to receive
     */
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

    /**
     * Method to check if the player has an item.
     *
     * @param itemName name of the item to check
     * @return true if the player has the item, false otherwise
     */
    public boolean hasItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public void heal(int heal) {
        this.health += heal;
    }

    /**
     * Method to automatically equip the best armor and sword.
     */
    public void autoEquipBest() {
        for (Item item : inventory) {
            if (item instanceof Helmet) {
                if (armor.getHelmet().getDefense() < ((Helmet) item).getDefense()) {
                    System.out.println("You equipped a better helmet.");
                    armor.setHelmet((Helmet) item);
                }
            } else if (item instanceof Chestplate) {
                if (armor.getChestplate().getDefense() < ((Chestplate) item).getDefense()) {
                    System.out.println("You equipped a better chestplate.");
                    armor.setChestplate((Chestplate) item);
                }
            } else if (item instanceof Leggings) {
                if (armor.getLeggings().getDefense() < ((Leggings) item).getDefense()) {
                    System.out.println("You equipped better leggings.");
                    armor.setLeggings((Leggings) item);
                }
            } else if (item instanceof Boots) {
                if (armor.getBoots().getDefense() < ((Boots) item).getDefense()) {
                    System.out.println("You equipped better boots.");
                    armor.setBoots((Boots) item);
                }
            } else if (item instanceof Sword) {
                if (sword.getDamage() < ((Sword) item).getDamage()) {
                    System.out.println("You equipped a better sword.");
                    sword = (Sword) item;
                }
            }
        }
    }
}
