package nl.rug.oop.introduction;

/**
 * This is the class to represent a person.
 */
public class Person {
    private String name;

    /**
     * Constructor to create a Person, which is used by lecturer, student and teaching assistant.
     * @param name Name of the person
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * This is a getter for the name.
     * @return the name of the person
     */
    public String getName() {
        return name;
    }
}
