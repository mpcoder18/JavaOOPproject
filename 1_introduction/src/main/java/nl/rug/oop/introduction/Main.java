package nl.rug.oop.introduction;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Class containing the main method.
 */
public class Main {
    /**
     * Main method.
     * @param args not used
     */
    public static void main(String[] args) {
        Lecturer lecturer = new Lecturer("name");
        List<Student> students = new ArrayList<>();
        students.add(new Student("Niels", 0));
        students.add(new Student("Mardo", 5));
        Assignment assignment1 = new Assignment("First Lab", LocalDateTime.of(2024, Month.APRIL, 1, 17, 0));
        Assignment assignment2 = new Assignment("Second Lab", LocalDateTime.of(2024, Month.APRIL, 29, 17, 0));
        Assignment assignment3 = new Assignment("Last Lab", LocalDateTime.of(2024, Month.APRIL, 29, 17, 0));
        TeachingAssistant matt = new TeachingAssistant("Matt");

        // Instruct student to do their assignments
        List<Submission> submissions = new ArrayList<>();
        for(Student student : students) {
            submissions.add(student.doAssignment(assignment1));
            submissions.add(student.doAssignment(assignment2));
        }

        for (int i = 0; i < 5; i++) {
            lecturer.lecture(students);
        }

        for(Student student : students) {
            submissions.add(student.doAssignment(assignment3));
        }

        matt.gradeAll(submissions);
    }
}