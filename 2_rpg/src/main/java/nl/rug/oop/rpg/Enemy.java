package nl.rug.oop.rpg;

/**
 * Class to represent enemies.
 */
public class Enemy extends NPC {
    private Game game;

    public Enemy(Game game, String description, int damage, int health) {
        super(description, damage, health);
        this.game = game;
    }

    public void attack(Player player) {
        player.health -= this.damage;
    }

    @Override
    public void interact(Player player) {
        System.out.println("You are fighting " + description);
        game.battle(player, this);
    }
}
