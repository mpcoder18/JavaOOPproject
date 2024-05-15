package nl.rug.oop.rpg.entities;

import nl.rug.oop.rpg.game.Combat;
import nl.rug.oop.rpg.game.Game;
import nl.rug.oop.rpg.interfaces.Attackable;
import nl.rug.oop.rpg.player.Player;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class to represent enemies.
 */
public class Enemy extends NPC implements Attackable, Serializable {
    @Serial
    private static final long serialVersionUID = 935159107779L;
    private final Game game;

    public Enemy(Game game, String description, int damage, int health) {
        super(description, damage, health);
        this.game = game;
    }

    /**
     * Method to attack a player.
     *
     * @param attackable entity to attack (Casted to Player)
     */
    public void attack(Attackable attackable) {
        Player player = (Player) attackable;
        player.receiveDamage(damage);
    }

    /**
     * Method to receive damage.
     *
     * @param damage amount of damage to receive
     */
    public void receiveDamage(int damage) {
        health -= damage;
        System.out.println("You attack the enemy and deal " + damage + " damage.");
        System.out.println("The enemy has " + health + " health left.");
    }

    @Override
    public void interact(Player player) {
        System.out.println("You are fighting " + description);
        Combat combat = new Combat(game, player, this);
        combat.interact();
    }
}
