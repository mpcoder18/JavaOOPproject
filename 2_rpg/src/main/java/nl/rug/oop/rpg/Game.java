package nl.rug.oop.rpg;

import java.util.List;
import java.util.Scanner;

/**
 * Class that contains the main game loop.
 */
public class Game {
    private Player player;
    private Scanner scanner;

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
    public void run () {
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
                    player.getCurrentRoom().lookForWayOut();
                    System.out.println("Which door do you take? (-1 : stay here)");
                    int option = scanner.nextInt();
                    List<Door> doors = player.getCurrentRoom().getDoors();
                    if (option < -1 || option > doors.size()) {
                        System.out.println("Invalid option");
                    } else if (option == -1) {
                        break;
                    } else {
                        doors.get(option).interact(player);
                    }
                    break;
                case 2:
                    player.getCurrentRoom().listCompany();
                    System.out.println("Interact ? (-1 : do nothing)");
                    int option2 = scanner.nextInt();
                    List<NPC> npcs = player.getCurrentRoom().getNpcs();
                    if (option2 < -1 || option2 > npcs.size()) {
                        System.out.println("Invalid option");
                    } else if (option2 == -1) {
                        break;
                    } else {
                        npcs.get(option2).interact(player);
                    }
                    break;
                default:
                    System.out.println("Invalid option");

            }
        }
    }
}
