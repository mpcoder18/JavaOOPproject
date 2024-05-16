package nl.rug.oop.rpg.environment;

import nl.rug.oop.rpg.player.Player;

/**
 * Class to represent an exit door. When the player interacts with it, the game ends.
 */
public class ExitDoor extends Door {
    public ExitDoor(String description, Room connectingRoom) {
        super(description, connectingRoom);
    }

    @Override
    public void interact(Player player) {
        System.out.println("You have exited the dungeon. Congratulations!");
        player.getGame().quitGame();
    }
}
