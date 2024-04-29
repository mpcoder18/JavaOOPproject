package nl.rug.oop.introduction;

import java.util.List;

/**
 * this is the lecturer class, an extension of person.
 */
public class Lecturer extends Person {
    /**
     * Method to create a lecturer.
     * @param name Name of the lecturer
     */
    public Lecturer(String name) {
        super(name);
    }

    /**
     * Method to give lectures to a list of students.
     * @param attendees Attendees that will attend the lecture
     */
    public void lecture(List<Student> attendees) {
        for(Student student : attendees) {
            student.obtainKnowledge();
        }
    }
}
