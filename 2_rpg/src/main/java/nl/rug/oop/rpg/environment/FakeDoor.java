package nl.rug.oop.rpg.environment;

import nl.rug.oop.rpg.player.Player;

import java.io.Serial;
import java.io.Serializable;

/**
 * A fake door that damages the player when interacted with.
 */
public class FakeDoor extends Door implements Serializable {
    @Serial
    private static final long serialVersionUID = 939959107779L;

    public FakeDoor(String description, Room connectingRoom) {
        super(description, connectingRoom);
    }

    @Override
    public void interact(Player player) {
        System.out.println("You tried opening the door, but it bit you. Ouch!");
        if (!getDescription().contains("(Fake)")) {
            setDescription(getDescription() + " (Fake)");
        }
        player.receiveDamage(1 + (int) (Math.random() * 5)); // Random damage between 1 and 5
    }
}
