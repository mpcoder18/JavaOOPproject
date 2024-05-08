package nl.rug.oop.rpg.interfaces;

import nl.rug.oop.rpg.player.Player;

/**
 * Interface for objects that can be interacted with.
 */
public interface Interactable {
    void interact(Player player);
}
