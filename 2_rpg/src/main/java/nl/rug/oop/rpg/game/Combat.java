package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.entities.NPC;
import nl.rug.oop.rpg.interfaces.Attackable;
import nl.rug.oop.rpg.player.Player;

/**
 * Class to represent combat between two attackable entities.
 */
public class Combat {
    private final Game game;
    private final Attackable attacker;
    private final Attackable opponent;

    /**
     * Constructor to create a new combat instance.
     *
     * @param game     Game in which the combat is happening
     * @param attacker Entity that is attacking (Player)
     * @param opponent Entity that is being attacked (Enemy)
     */
    public Combat(Game game, Attackable attacker, Attackable opponent) {
        this.game = game;
        this.attacker = attacker;
        this.opponent = opponent;
    }

    private boolean isAlive(Attackable entity) {
        return entity.getHealth() > 0;
    }

    /**
     * Method to start the combat loop.
     */
    public void interact() {
        while (isAlive(attacker) && isAlive(opponent)) {
            attacker.attack(opponent);
            if (isAlive(opponent)) {
                opponent.attack(attacker);
            }
        }
        if (!isAlive(attacker)) {
            System.out.println("You died");
            game.gameOver();
        } else {
            int moneyReward = (int) (Math.random() * 100);
            System.out.println("You won the fight and earned " + moneyReward + " gold.");
            ((Player) attacker).setMoney(((Player) attacker).getMoney() + moneyReward);
            game.removeNPC((NPC) opponent);
        }
    }
}
