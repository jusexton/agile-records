package main.java.users.students;

/**
 * Represents a grade a student has earned.
 */
public class Grade {
    private double score;
    private GradeType type;
    private String name;

    public Grade(double score, GradeType type) {
        this.score = score;
        this.type = type;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public GradeType getType() {
        return type;
    }

    public void setType(GradeType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
