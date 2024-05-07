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
        Room startRoom = new Room("The entrance of the dungeon.");
        Room room1 = new Room("A dark room.");
        Room room2 = new Room("A blue room.");
        startRoom.addDoor(new Door("A red door.", room1));
        startRoom.addDoor(new Door("A black door.", room2));
        room1.addDoor(new Door("Door to head back to the start area", startRoom));
        room2.addDoor(new Door("A yellow door", room1));
        startRoom.addNPC(new NPC("Lost villager", 0, 100));
        startRoom.addNPC(new NPC("Homeless guy", 0, 100));
        Player player = new Player("John", startRoom, 100, 10);

        Game game = new Game(player, scanner);

        startRoom.addNPC(new Enemy(game,"Spider", 3, 20));
        startRoom.addNPC(new Enemy(game,"Goblin", 5, 30));
        room1.addNPC(new Enemy(game,"Orc", 10, 50));
        room1.addNPC(new Enemy(game,"Skeleton", 7, 40));
        room2.addNPC(new Enemy(game,"Dragon", 20, 100));

        game.run();
    }
}
