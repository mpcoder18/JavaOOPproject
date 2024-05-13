package nl.rug.oop.rpg.environment;

import nl.rug.oop.rpg.game.Game;
import nl.rug.oop.rpg.player.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import java.util.Random;

public class RandomDoor extends Door implements Serializable {
    @Serial
    private static final long serialVersionUID = 930359107744L;
    private Game game;

    public RandomDoor(Game game, String description, Room room){
        super(description, room);
        this.game = game;
    }

    @Override
    public void interact(Player player) {
        List<Room> rooms = game.getRooms();

        Random random = new Random();
        int randomIndex = random.nextInt(rooms.size()); // Generate random index between 0 (inclusive) and colors.size() (exclusive)

        Room randomRoom = rooms.get(randomIndex);
        player.setCurrentRoom(randomRoom);
        System.out.println("You have been teleported to a random room!");
    }
}
