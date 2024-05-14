package nl.rug.oop.rpg.environment;

import nl.rug.oop.rpg.game.Game;
import nl.rug.oop.rpg.player.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import java.util.Random;

/**
 * Class that represents a door that teleports the player to a random room.
 */
public class RandomDoor extends Door implements Serializable {
    @Serial
    private static final long serialVersionUID = 930359107744L;
    private final Game game;

    public RandomDoor(Game game, String description, Room room) {
        super(description, room);
        this.game = game;
    }

    @Override
    public void interact(Player player) {
        List<Room> rooms = game.getRooms();

        Random random = new Random();
        int randomIndex = random.nextInt(rooms.size()); // Random index between 0 and rooms.size() - 1

        Room randomRoom = rooms.get(randomIndex);
        player.setCurrentRoom(randomRoom);
        System.out.println("You have been teleported to a random room!");
    }
}
