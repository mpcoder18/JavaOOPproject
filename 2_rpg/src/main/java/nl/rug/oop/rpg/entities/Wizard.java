package nl.rug.oop.rpg.entities;

import nl.rug.oop.rpg.ChoiceMenu;
import nl.rug.oop.rpg.game.Game;
import nl.rug.oop.rpg.inventory.items.Sword;
import nl.rug.oop.rpg.player.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * Class that represents a wizard NPC.
 */
public class Wizard extends NPC implements Serializable {
    @Serial
    private static final long serialVersionUID = 930359109899L;
    private Game game;

    /**
     * Constructor to create a new NPC.
     *
     * @param game        Instance of the game
     * @param description Description of the NPC
     * @param damage      Damage the NPC can deal
     * @param health      Health of the NPC
     */
    public Wizard(Game game, String description, int damage, int health) {
        super(description, damage, health);
        this.game = game;
    }

    /**
     * Method to enchant a sword.
     *
     * @param player player to enchant the sword for
     * @param sword  sword to enchant
     */
    public void enchantItem(Player player, Sword sword) {
        if (player.getMoney() < 10) {
            System.out.println("You do not have enough money to enchant your sword.");
            System.out.print("You currently have " + player.getMoney() + " gold. ");
            System.out.println("(Kill enemies or sell items to get more gold)");
            return;
        }
        Random random = new Random();
        int damage = random.nextInt(5) + 1;
        sword.setDamage(sword.getDamage() + damage);
        player.setMoney(player.getMoney() - 10);
        System.out.println("You have enchanted your sword with " + damage + " damage.");
    }

    @Override
    public void interact(Player player) {
        System.out.println("You are talking to " + description);
        System.out.println("You can pay 10 gold to enchant your sword.");
        ChoiceMenu choiceMenu = new ChoiceMenu("Do you want to enchant your sword?");
        choiceMenu.addChoice(0, () -> enchantItem(player, player.getSword()), "Yes");
        choiceMenu.addChoice(1, () -> System.out.println("You decide not to enchant your sword."), "No");
        choiceMenu.run(game.getScanner());
    }
}
