package nl.rug.oop.rpg;

/**
 * Class to represent a door.
 */
public class Door implements Inspectable {
    private String description;

    /**
     * Constructor to create a new door.
     * @param description Description of the new door
     */
    public Door(String description) {
        this.description = description;
    }

    /**
     * Method to inspect the door (print its description).
     */
    public void inspect() {
        System.out.println(description);
    }
}
