package nl.rug.oop.rpg.environment;

import nl.rug.oop.rpg.ChoiceMenu;
import nl.rug.oop.rpg.game.Game;
import nl.rug.oop.rpg.interfaces.Interactable;
import nl.rug.oop.rpg.player.Player;

/**
 * Class to represent a locked door.
 */
public class LockedDoor extends Door implements Interactable {
    private boolean locked;
    private final Game game;

    /**
     * Constructor to create a new locked door.
     *
     * @param game        Game in which the door is
     * @param description Description of the door
     * @param room        Room to which the door connects
     */
    public LockedDoor(Game game, String description, Room room) {
        super(description, room);
        locked = true;
        this.game = game;
    }

    /**
     * Method to interact with the door.
     *
     * @param player Player interacting with the door
     */
    public void interact(Player player) {
        if (locked) {
            if (player.hasItem("Key")) {
                ChoiceMenu choiceMenu = new ChoiceMenu("Unlock the door?");
                choiceMenu.addChoice(0, () -> {
                    locked = false;
                    System.out.println("The door is now unlocked.");
                    this.setDescription(this.getDescription() + " (unlocked)");
                }, "Yes");
                choiceMenu.addChoice(1, () -> {
                }, "No");
                choiceMenu.run(game.getScanner());
            }
        } else {
            player.setCurrentRoom(this.getConnectingRoom());
        }
    }
}
