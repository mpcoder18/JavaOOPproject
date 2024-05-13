package nl.rug.oop.rpg.environment;

import nl.rug.oop.rpg.player.Player;

/**
 * A fake door that damages the player when interacted with
 */
public class FakeDoor extends Door {
    public FakeDoor(String description, Room connectingRoom){
        super(description, connectingRoom);
    }

    @Override
    public void interact(Player player) {
        System.out.println("You tried opening the door, but it bit you. Ouch!");
        player.receiveDamage(1 + (int) (Math.random() * 5)); // Random damage between 1 and 5
    }
}
