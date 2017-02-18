package main.java.users.students;

import main.java.users.User;
import main.java.util.security.Hash;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents students on the sql server.
 */
public class Student extends User {
    private List<Course> courses;

    public Student(String userName, Hash password) {
        super(userName, password);
        courses = new ArrayList<>();
    }

    public double getGPA() {
        int hours = 0;
        int totalHours = 0;
        double total = 0;
        for (Course registeredCourse : this.courses) {
            hours = registeredCourse.getCreditHours();
            total += registeredCourse.getAverage() * hours;
            totalHours += hours;
        }
        return total / totalHours;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
