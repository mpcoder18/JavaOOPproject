package nl.rug.oop.rpg.environment;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rpg.interfaces.Inspectable;
import nl.rug.oop.rpg.interfaces.Interactable;
import nl.rug.oop.rpg.player.Player;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class to represent a door.
 */
@Getter
public class Door implements Inspectable, Interactable, Serializable {
    @Serial
    private static final long serialVersionUID = 939259107779L;
    @Setter
    private String description;
    private final Room connectingRoom;

    /**
     * Constructor to create a new door.
     *
     * @param description    Description of the new door
     * @param connectingRoom Room to which the door connects
     */
    public Door(String description, Room connectingRoom) {
        this.description = description;
        this.connectingRoom = connectingRoom;
    }

    /**
     * Method to inspect the door (print its description).
     */
    public void inspect() {
        System.out.println(description);
    }

    public void interact(Player player) {
        player.setCurrentRoom(this.connectingRoom);
    }
}
