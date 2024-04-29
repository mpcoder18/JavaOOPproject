package nl.rug.oop.introduction;

import java.time.LocalDateTime;

/**
 * Class to represent an assignment.
 */
public class Assignment {
    private String name;
    private final LocalDateTime deadline;

    /**
     * Constructor to create a new assignment.
     * @param name Name of the assignment
     * @param deadline LocalDateTime of the deadline to the assignment
     */
    public Assignment(String name, LocalDateTime deadline){
        this.name = name;
        this.deadline = deadline;

    }

    /**
     * Getter method for the deadline to the assignment.
     * @return The deadline of the assignment
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Getter method for the name of the assignment.
     * @return The name of the assignment
     */
    public String getName() {
        return name;
    }
}
