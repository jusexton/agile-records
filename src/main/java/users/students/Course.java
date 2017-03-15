package main.java.users.students;

import main.java.util.Interval;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course that belongs to a student.
 */
public class Course {
    private int CRN;
    private String name;
    private List<Grade> grades;
    private int creditHours;
    private Interval dateInterval;
    private Interval timeInterval;

    public Course(String name, int creditHours, int CRN) {
        this.name = name;
        this.creditHours = creditHours;
        this.CRN = CRN;
        this.grades = new ArrayList<>();
    }

    public int getAverage() {
        double total = 0;
        for (Grade grade : grades) {
            total += grade.getScore();
        }

        total /= grades.size();
        if (total >= 90) {
            return 4;
        } else if (total >= 80) {
            return 3;
        } else if (total >= 70) {
            return 2;
        } else if (total >= 60) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getCRN() {
        return CRN;
    }

    public void setCRN(int CRN) {
        this.CRN = CRN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public Interval getDateInterval() {
        return dateInterval;
    }

    public void setDateInterval(Interval dateInterval) {
        this.dateInterval = dateInterval;
    }

    public Interval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Interval timeInterval) {
        this.timeInterval = timeInterval;
    }
}
