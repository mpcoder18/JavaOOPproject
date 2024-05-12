package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.ChoiceMenu;
import nl.rug.oop.rpg.entities.Enemy;
import nl.rug.oop.rpg.entities.NPC;
import nl.rug.oop.rpg.environment.Door;
import nl.rug.oop.rpg.player.Player;

import java.util.List;
import java.util.Scanner;

/**
 * Class that contains the main game loop.
 */
public class Game {
    private final Player player;
    private final Scanner scanner;

    /**
     * Constructor for the Game class.
     * @param player Player of the game
     * @param scanner Scanner to get input from the terminal
     */
    public Game(Player player, Scanner scanner) {
        this.player = player;
        this.scanner = scanner;
    }

    /**
     * Method to run the main game loop.
     */
    public void run() {
        System.out.println("Welcome to the game!");
        ChoiceMenu choiceMenu = new ChoiceMenu("What do you want to do? (-1 to do nothing)");
        choiceMenu.addChoice(0, () -> player.getCurrentRoom().inspect(), "Look around");
        choiceMenu.addChoice(1, this::chooseDoor, "Look for a way out");
        choiceMenu.addChoice(2, this::interactWithNPC, "Look for company");
        choiceMenu.addChoice(3, this::quitGame, "Quit game");
        while (true) {
            choiceMenu.run(scanner);
        }
    }

    private void chooseDoor() {
        ChoiceMenu choiceMenu = new ChoiceMenu("Which door do you want to take? (-1 to stay here)");
        List<Door> doors = player.getCurrentRoom().getDoors();
        int counter = 0;
        for (Door door : doors) {
            choiceMenu.addChoice(counter++, () -> door.interact(player), door.getDescription());
        }
        choiceMenu.addChoice(-1, () -> {}, "Stay here");
        choiceMenu.run(scanner);
    }

    private void interactWithNPC() {
        int counter = 0;
        ChoiceMenu choiceMenu = new ChoiceMenu("Which creature do you want to interact with? (-1 to go back)");
        for (NPC npc : player.getCurrentRoom().getNpcs()) {
            choiceMenu.addChoice(counter++, () -> npc.interact(player), npc.getDescription());
        }
        choiceMenu.run(scanner);
    }

    public void gameOver() {
        System.out.println("Game over");
        System.exit(0);
    }

    private void quitGame() {
        System.out.println("Goodbye!");
        System.exit(0);
    }

    public void removeNPC(NPC npc) {
        player.getCurrentRoom().getNpcs().remove(npc);
    }
}
