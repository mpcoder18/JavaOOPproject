package nl.rug.oop.introduction;

/**
 * Class to represent a Student.
 */
public class Student extends Person {
    private int knowledgeLevel;

    /**
     * Constructor to create a new student.
     * @param name Name of the student
     * @param knowledgeLevel Knowledge level of the student
     */
    public Student(String name, int knowledgeLevel) {
        super(name);
        this.knowledgeLevel = knowledgeLevel;
    }

    /**
     * Method to obtain knowledge (increments knowledge level up to 6).
     */
    public void obtainKnowledge() {
        knowledgeLevel = Math.min(knowledgeLevel + 1, 6);
    }

    /**
     * Getter for the knowledge level of the student.
     * @return the knowledge level
     */
    public int getKnowledgeLevel() {
        return knowledgeLevel;
    }

    /**
     * Method to create a new submission for an assignment.
     * @param assignment the assignment to make a new submission to
     * @return the newly created submission
     */
    public Submission doAssignment(Assignment assignment) {
        return new Submission(assignment, this);
    }
}
