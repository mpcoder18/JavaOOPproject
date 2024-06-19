package nl.rug.oop.rts;

public interface Command {
    void execute();
    void undo();
}
