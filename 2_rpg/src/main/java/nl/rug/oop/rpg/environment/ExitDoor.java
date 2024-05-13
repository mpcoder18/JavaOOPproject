package nl.rug.oop.rpg.environment;

import nl.rug.oop.rpg.player.Player;

public class ExitDoor extends Door {
    public ExitDoor(String description, Room connectingRoom) {
        super(description, connectingRoom);
    }

    @Override
    public void interact(Player player) {
        System.out.println("You have exited the dungeon. Congratulations!");
        System.exit(0);
    }
}
