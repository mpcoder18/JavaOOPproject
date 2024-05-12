package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.entities.*;
import nl.rug.oop.rpg.environment.Door;
import nl.rug.oop.rpg.environment.LockedDoor;
import nl.rug.oop.rpg.environment.Room;
import nl.rug.oop.rpg.inventory.Item;
import nl.rug.oop.rpg.player.Player;

import java.util.Scanner;

/**
 * Class that creates the game.
 */
public class CreateGame {
    public CreateGame() {

    }

    /**
     * Method to run the game.
     */
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        Room startRoom = new Room("The entrance of the dungeon.");
        Room room1 = new Room("A dark room.");
        Room room2 = new Room("A blue room.");
        Room room3 = new Room("A room with a chest.");
        startRoom.addDoor(new Door("A red door.", room1));
        startRoom.addDoor(new Door("A black door.", room2));
        room1.addDoor(new Door("Door to head back to the start area", startRoom));
        room2.addDoor(new Door("A yellow door", room1));
        startRoom.addNPC(new Wizard("A boring wizard", 100, 100));
        startRoom.addNPC(new Healer("A medic", 1, 10000));
        room1.addNPC(new Trader("A suspicious trader.", 100, 100));
        Player player = new Player("John", startRoom, 100, 10);

        Game game = new Game(player, scanner);

        startRoom.addDoor(new LockedDoor(game, "A locked door", room3));
        startRoom.addNPC(new Enemy(game, "Spider", 3, 20));
        startRoom.addNPC(new Enemy(game, "Goblin", 5, 30));
        room1.addNPC(new Enemy(game, "Orc", 10, 50));
        room1.addNPC(new Enemy(game, "Skeleton", 7, 40));
        room2.addNPC(new Enemy(game, "Dragon", 20, 100));

        player.addItem(new Item("key", "A door with a lock", 100));

        game.run();
    }
}
