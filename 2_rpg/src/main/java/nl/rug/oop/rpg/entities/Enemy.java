package nl.rug.oop.rpg.entities;

import nl.rug.oop.rpg.interfaces.Attackable;
import nl.rug.oop.rpg.game.Game;
import nl.rug.oop.rpg.player.Player;

/**
 * Class to represent enemies.
 */
public class Enemy extends NPC implements Attackable {
    private final Game game;

    public Enemy(Game game, String description, int damage, int health) {
        super(description, damage, health);
        this.game = game;
    }

    /**
     * Method to attack a player.
     * @param attackable entity to attack (Casted to Player)
     */
    public void attack(Attackable attackable) {
        Player player = (Player) attackable;
        player.setHealth(player.getHealth() - this.damage);
        System.out.println("The enemy attack and deals " + this.damage + " damage");;
    }

    @Override
    public void interact(Player player) {
        System.out.println("You are fighting " + description);
        game.battle(player, this);
    }
}
