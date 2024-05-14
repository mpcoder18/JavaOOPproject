package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.environment.*;
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
        roomList.add(startRoom);
        Player player = new Player("John", startRoom, 100);
        Game game = new Game(player, scanner, roomList);
        player.setGame(game);
        GenerateDungeon dungeon = new GenerateDungeon(game);
        roomList = dungeon.generateDungeon(startRoom, 3);
        game.setRooms(roomList);
        game.run();
    }
}
