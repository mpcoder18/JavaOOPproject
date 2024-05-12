package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.entities.NPC;
import nl.rug.oop.rpg.interfaces.Attackable;

public class Combat {
    private Game game;
    private Attackable attacker;
    private Attackable opponent;

    public Combat(Game game, Attackable attacker, Attackable opponent) {
        this.game = game;
        this.attacker = attacker;
        this.opponent = opponent;
    }

    private boolean isAlive(Attackable entity) {
        return entity.getHealth() > 0;
    }

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
            System.out.println("You won");
            game.removeNPC((NPC) opponent);
        }
    }
}
