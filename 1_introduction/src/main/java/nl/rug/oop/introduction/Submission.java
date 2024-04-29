package nl.rug.oop.introduction;

import java.time.LocalDateTime;

/**
 * Class for submissions to assignments.
 */
public class Submission {
    private int quality;
    private LocalDateTime submittedDateTime;
    private Assignment assignment;
    private Student student;

    /**
     * Constructor to create a new Submission.
     * @param assignment Assignment for which the submission is for
     * @param student Student which did the submission
     */
    public Submission(Assignment assignment, Student student) {
        this.assignment = assignment;
        this.student = student;
        this.quality = student.getKnowledgeLevel();
        this.submittedDateTime = LocalDateTime.now();
    }

    /**
     * Getter method to get the assignment for which the submission was made.
     * @return the assignment linked to the submission
     */
    public Assignment getAssignment() {
        return assignment;
    }

    /**
     * Get the date of the submission of the assignment.
     * @return The date of the submission
     */
    public LocalDateTime getSubmittedDateTime() {
        return submittedDateTime;
    }

    /**
     * Get the quality of the submission.
     * @return the quality of the submission
     */
    public int getQuality() {
        return quality;
    }

    /**
     * Get the student who did the submission.
     * @return the student who did the submission
     */
    public Student getStudent() {
        return student;
    }
}
