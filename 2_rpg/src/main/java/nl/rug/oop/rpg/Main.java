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
        Scanner scanner = new Scanner(System.in);
        Room startRoom = new Room("A rather dusty room full of computers.");
        Room room1 = new Room("A dark room.");
        Room room2 = new Room("A blue room.");
        startRoom.addDoor(new Door("A red door.", room1));
        startRoom.addDoor(new Door("A black door.", room2));
        room1.addDoor(new Door("Door to head back to the start area", startRoom));
        room2.addDoor(new Door("A yellow door", room1));
        startRoom.addNPC(new NPC("Test NPC"));
        Player player = new Player("John", startRoom);

        Game game = new Game(player, scanner);
        game.run();


    }
}
