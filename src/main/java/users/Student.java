package main.java.users;

/**
 * Represents a student on the sql server.
 */
public class Student extends User{

    private double gpa;
    private List<Course> registeredCourses;

    public void addCourse(int crn, string name,List<Double> g, int h)
    {
        c = new Course(crn, name, g, h);
        registeredCourses.add(c);
        delete c;
    }
    public void setGPA(){
        int size = registeredCourses.size();
        int totalHours = 0;
        double total = 0;
        for(int index = 0; index < size; index++) {
            h = registeredCourses.index.getCreditHours();
            total += registeredCourses.index.getClassAvg()*h;
            totalHours +=h;
        }
        gpa = total/totalHours;

    }
    public double getGPA(){
        return gpa;
    }



}

