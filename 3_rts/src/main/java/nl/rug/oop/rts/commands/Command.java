package nl.rug.oop.rts.commands;

/**
 * Interface for commands.
 */
public interface Command {
    void execute();

    void undo();
}
