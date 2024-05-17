package nl.rug.oop.rpg.menus;

import nl.rug.oop.rpg.entities.NPC;
import nl.rug.oop.rpg.game.Game;
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
        System.out.println("You have " + (attacker.getHealth() + ((Player) attacker).getArmor().getDefense())
                + " health (+ armor) and your sword deals " + ((Player) attacker).getSword().getDamage() + " damage.");
        System.out.println("The enemy has " + opponent.getHealth() + " health and deals " +
                ((NPC) opponent).getDamage() + " damage.");

        ChoiceMenu choiceMenu = new ChoiceMenu("What do you want to do?");
        choiceMenu.addChoice(0, this::fightEnemy, "Fight");
        choiceMenu.addChoice(1, () -> System.out.println("You run away"), "Run away");
        choiceMenu.run(game.getScanner());
    }

    private void fightEnemy() {
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
