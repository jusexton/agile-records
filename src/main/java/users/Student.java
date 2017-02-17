package main.java.users.student;

import main.java.users.User;
import main.java.util.security.Hash;

/**
 * Class that represents students on the sql server.
 */
public class Student extends User {
    public Student(String userName, Hash password, int ID) {
        super(userName, password, ID);
    }

    private double GPA;
    private List<Course> registeredCourses;

    public void addCourse(int CRN, string name,List<Double> grades, int creditHours)
    {
        course = new Course(CRN, name, grades, creditHours);
        registeredCourses.add(course);
    }

    public void setGPA(){
        int size = registeredCourses.size();
        int totalHours = 0;
        double total = 0;
        for(int index = 0; index < size; index++) {
            hours = registeredCourses.index.getCreditHours();
            total += registeredCourses.index.getClassAvg()*h;
            totalHours +=hours;
        }
        this.GPA = total/totalHours;

    }
    public double getGPA(){
        return GPA;
    }

    public List<Course> getRegisteredCourses(){
        return registereCourses
    }

    public void setRegisteredCourses(List<Course> courses){
        this.registeredCourses = courses;
    }


}
