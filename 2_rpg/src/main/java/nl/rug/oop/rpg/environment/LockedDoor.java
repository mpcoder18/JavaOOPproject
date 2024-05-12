package nl.rug.oop.rpg.environment;

import nl.rug.oop.rpg.ChoiceMenu;
import nl.rug.oop.rpg.game.Game;
import nl.rug.oop.rpg.interfaces.Interactable;
import nl.rug.oop.rpg.player.Player;

public class LockedDoor extends Door implements Interactable {
    private boolean locked;
    Game game;

    public LockedDoor(Game game, String description, Room room) {
        super(description, room);
        locked = true;
        this.game = game;
    }

    public void interact(Player player) {
        if (locked) {
            if (player.getInventory().stream().anyMatch(item -> item.getName().equals("key"))) {
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
