package nl.rug.oop.rts;

/**
 * Interface for commands.
 */
public interface Command {
    void execute();

    void undo();
}
