package nl.rug.oop.rts.graph;

import nl.rug.oop.rts.objects.Army;

import java.util.List;

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

    String getName();

    void setName(String name);

    List<Army> getArmies();
}
