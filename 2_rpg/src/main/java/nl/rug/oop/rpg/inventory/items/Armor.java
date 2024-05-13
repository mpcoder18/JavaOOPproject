package nl.rug.oop.rpg.inventory.items;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rpg.inventory.items.armor.Boots;
import nl.rug.oop.rpg.inventory.items.armor.Chestplate;
import nl.rug.oop.rpg.inventory.items.armor.Helmet;
import nl.rug.oop.rpg.inventory.items.armor.Leggins;

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
    private Leggins leggins;
    private Boots boots;

    /**
     * Constructor to create a new armor.
     */
    public Armor() {
        helmet = new Helmet("A helmet", 10, 2);
        chestplate = new Chestplate("A chestplate", 20, 3);
        leggins = new Leggins("A pair of leggins", 15, 1);
        boots = new Boots("A pair of boots", 10, 1);
    }

    public int getDefense() {
        return helmet.getDefense() + chestplate.getDefense() + leggins.getDefense() + boots.getDefense();
    }
}
