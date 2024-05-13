package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.entities.*;
import nl.rug.oop.rpg.environment.*;
import nl.rug.oop.rpg.inventory.Item;
import nl.rug.oop.rpg.inventory.items.Key;
import nl.rug.oop.rpg.player.Player;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
        List<Room> roomList = new ArrayList<>();
        Room startRoom = new Room("The entrance of the dungeon.");
        Room room1 = new Room("A dark room.");
        Room room2 = new Room("A blue room.");
        Room room3 = new Room("A room with a chest.");
        roomList.add(startRoom);
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);
        startRoom.addDoor(new Door("A red door.", room1));
        startRoom.addDoor(new Door("A black door.", room2));
        startRoom.addDoor(new FakeDoor("A suspicious door", room3));
        room1.addDoor(new Door("Door to head back to the start area", startRoom));
        room2.addDoor(new Door("A yellow door", room1));
        startRoom.addNPC(new Wizard("A boring wizard", 100, 100));
        startRoom.addNPC(new Healer("A medic", 1, 10000));
        room1.addNPC(new Trader("A suspicious trader.", 100, 100));
        Player player = new Player("John", startRoom, 100);

        Game game = new Game(player, scanner, roomList);

        startRoom.addDoor(new LockedDoor(game, "A locked door", room3));
        startRoom.addNPC(new Enemy(game, "Spider", 3, 20));
        startRoom.addNPC(new Enemy(game, "Goblin", 5, 30));
        startRoom.addDoor(new RandomDoor(game, "A mysterious door", roomList.get(0)));
        room1.addNPC(new Enemy(game, "Orc", 10, 50));
        room1.addNPC(new Enemy(game, "Skeleton", 7, 40));
        room2.addNPC(new Enemy(game, "Dragon", 20, 100));

        player.addItem(new Key("Used to unlock doors", 100));

        game.run();
    }
}
