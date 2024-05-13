package nl.rug.oop.rpg.entities;

import nl.rug.oop.rpg.game.Combat;
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
     *
     * @param attackable entity to attack (Casted to Player)
     */
    public void attack(Attackable attackable) {
        Player player = (Player) attackable;
        player.receiveDamage(damage);
    }

    @Override
    public void interact(Player player) {
        System.out.println("You are fighting " + description);
        Combat combat = new Combat(game, player, this);
        combat.interact();
    }
}
