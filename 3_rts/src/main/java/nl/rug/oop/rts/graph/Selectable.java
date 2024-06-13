package nl.rug.oop.rts.graph;

/**
 * Interface for selectable objects.
 */
public interface Selectable {
    default void select() {
        this.setSelected(true);
    }

    default void deselect() {
        this.setSelected(false);
    }

    void setSelected(boolean selected);
}
