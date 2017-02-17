package main.java.users.students;

import main.java.users.User;
import main.java.util.security.Hash;

/**
 * Class that represents students on the sql server.
 */
public class Student extends User {
    private double GPA;
    private List<Course> registeredCourses;
    
    public Student(String userName, Hash password) {
        super(userName, password);
    }

    public void addCourse(String name, int creditHours, int CRN, List<Double> grades, LocalDateTime time){
        course = new Course(String name, int creditHours, int CRN);
        course.setGrades(grades);
        course.setTime(time);
        this.registeredCourses.add(course);

    }
    public double getGPA(){return GPA}
    void setGPA(){
        int size = this.registeredCourses.size();
        int hours = 0;
        int totalHours = 0;
        double total = 0;
        for(int index = 0; index < size; index++)
        {
            hours = this.registeredCourses.get(index).getCreditHours();
            total += this.registeredCourses.get(index).getAverage() * hours;
            totalHours += hours;
        }
        this.GPA =  total/totalHours;
    }
    public return List<course> getRegisteredCourses(){ return registeredCourses}
    public void setRegisteredCourses(List<course> courses){ this.registeredCourses = courses}


}
