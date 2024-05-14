package nl.rug.oop.rpg.inventory.items;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rpg.inventory.items.armor.Boots;
import nl.rug.oop.rpg.inventory.items.armor.Chestplate;
import nl.rug.oop.rpg.inventory.items.armor.Helmet;
import nl.rug.oop.rpg.inventory.items.armor.Leggings;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class that represents the armor of the player.
 */
@Getter
@Setter
public class Armor implements Serializable {
    @Serial
    private static final long serialVersionUID = 220359107779L;
    private Helmet helmet;
    private Chestplate chestplate;
    private Leggings leggings;
    private Boots boots;

    /**
     * Constructor to create a new armor.
     */
    public Armor() {
        helmet = new Helmet("A helmet", 10, 2);
        chestplate = new Chestplate("A chestplate", 20, 3);
        leggings = new Leggings("A pair of leggings", 15, 1);
        boots = new Boots("A pair of boots", 10, 1);
    }

    /**
     * Method to get the total defense of the armor.
     *
     * @return Total defense of the armor
     */
    public int getDefense() {
        int totalDefense = 0;
        if (helmet != null) {
            totalDefense += helmet.getDefense();
        }
        if (chestplate != null) {
            totalDefense += chestplate.getDefense();
        }
        if (leggings != null) {
            totalDefense += leggings.getDefense();
        }
        if (boots != null) {
            totalDefense += boots.getDefense();
        }
        return totalDefense;
    }
}
