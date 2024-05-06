package nl.rug.oop.rpg;

import java.util.Scanner;

/**
 * Class that contains the main method.
 */
public class Main {
    /**
     * Main method.
     * @param args Arguments from the input
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Room startRoom = new Room("A rather dusty room full of computers.");
        Player player = new Player("John", startRoom);

        while(true) {
            System.out.println("What do you want to do?");
            System.out.println("  (0) Look around");
            switch (sc.nextInt()) {
                case 0:
                    player.getCurrentRoom().inspect();
                    break;
                default:
                    System.out.println("Invalid option");

            }
        }
    }
}
