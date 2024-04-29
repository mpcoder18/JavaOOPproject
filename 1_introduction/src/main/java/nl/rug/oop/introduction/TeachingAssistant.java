package nl.rug.oop.introduction;

import java.util.List;
import java.util.Random;

/**
 * Class to represent a teaching assistant.
 */
public class TeachingAssistant extends Person {
    /**
     * Constructor to create a new teaching assistant.
     * @param name Name of the teaching assistant
     */
    public TeachingAssistant(String name) {
        super(name);
    }

    /**
     * Method to grade a single submission.
     * @param submission The submission to grade
     */
    public void grading(Submission submission){
        int grade = 0;
        // If the submission was submitted before the deadline, calculate the grade based on the quality
        if(submission.getSubmittedDateTime().isBefore(submission.getAssignment().getDeadline())) {
            Random random = new Random();
            // Random number between submission quality and 10 (quality is at most 6)
            grade = random.nextInt(5) + submission.getQuality();
        }

        System.out.print("TA " + this.getName() + " graded the work of " + submission.getStudent().getName());
        System.out.print(" (" + submission.getAssignment().getName() + ") and gave his work a " + grade);
    }

    /**
     * Method to grade all the submissions.
     * @param submissions List of submissions to grade
     */
    public void gradeAll(List<Submission> submissions){
        for (Submission submission : submissions) {
            grading(submission);
        }
    }
}
