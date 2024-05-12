package nl.rug.oop.rpg.interfaces;

/**
 * Interface for attackable entities.
 */
public interface Attackable {
    void attack(Attackable attackable);

    int getHealth();
}
