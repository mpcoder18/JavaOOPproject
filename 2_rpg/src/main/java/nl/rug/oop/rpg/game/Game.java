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
        System.out.println("Interact ? (-1 : do nothing)");
        int option = scanner.nextInt();
        List<NPC> npcs = player.getCurrentRoom().getNpcs();
        if (option < -1 || option > npcs.size()) {
            System.out.println("Invalid option");
        } else if (option != -1) {
            npcs.get(option).interact(player);
        }
    }

    /**
     * Method to start a battle between the player and an enemy.
     * @param player Current player
     * @param enemy Enemy to battle against
     */
    public void battle(Player player, Enemy enemy) {
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            System.out.println("Choose your action:");
            System.out.println("  (0) Attack");
            System.out.println("  (1) Run");
            switch (scanner.nextInt()) {
                case 0:
                    player.attack(enemy);
                    enemy.attack(player);
                    System.out.println("You have " + player.getHealth() + " health left.");
                    System.out.println("The enemy has " + enemy.getHealth() + " health left.");
                    break;
                case 1:
                    System.out.println("You run away.");
                    return;
                default:
                    System.out.println("Invalid option");
            }

            if (player.getHealth() <= 0) {
                System.out.println("You died.");
                System.exit(0);
            } else if (enemy.getHealth() <= 0) {
                System.out.println("You defeated the enemy.");
                player.getCurrentRoom().getNpcs().remove(enemy);
                return;
            }
        }
    }
}
