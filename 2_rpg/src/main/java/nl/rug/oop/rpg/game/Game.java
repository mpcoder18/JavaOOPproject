package nl.rug.oop.rpg.game;

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
        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("  (0) Look around");
            System.out.println("  (1) Look for a way out");
            System.out.println("  (2) Look for company");
            switch (scanner.nextInt()) {
                case 0:
                    player.getCurrentRoom().inspect();
                    break;
                case 1:
                    chooseDoor();
                    break;
                case 2:
                    interactWithNPC();
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void chooseDoor() {
        player.getCurrentRoom().lookForWayOut();
        System.out.println("Which door do you take? (-1 : stay here)");
        int option = scanner.nextInt();
        List<Door> doors = player.getCurrentRoom().getDoors();
        if (option < -1 || option > doors.size()) {
            System.out.println("Invalid option");
        } else if (option != -1) {
            doors.get(option).interact(player);
        }
    }

    private void interactWithNPC() {
        player.getCurrentRoom().listCompany();
        System.out.println("Which creature do you want to interact with? (-1 : none)");
        int option = scanner.nextInt();
        List<NPC> npcs = player.getCurrentRoom().getNpcs();
        if (option < -1 || option > npcs.size() - 1) {
            System.out.println("Invalid option");
        } else if (option != -1) {
            npcs.get(option).interact(player);
        }
    }

    public void gameOver() {
        System.out.println("Game over");
        System.exit(0);
    }

    public void removeNPC(NPC npc) {
        player.getCurrentRoom().getNpcs().remove(npc);
    }
}
