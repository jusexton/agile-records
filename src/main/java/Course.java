
/**
 * Created by germ21 on 2/14/2017.
 */

package main.java.course;
package main.java.time.LocalDateTime;
 /* Represents a course on the sql server.
 */
public class Course {
    private int crn;
    private string courseName;
    private List<Double> grades =  new ArrayList<Double>();
    private int creditHours;
    private LocalDateTime time;

    public void setCRN(int num){
        crn = num;
    }

    public void setCourseName(string s){
        courseName = s;
    }
    public void setCreditHours(int h){
        creditHours = h;
    }

    public void addGrade(double g){
        list.add(g);
    }
    public int getCRN(){
        return crn;
    }
    public int getCourseNames(){
        return crn;
    }
    public List<Double> getGrades(){
        return grades;
    }
    public int getCreditHours()
    {
        return creditHours;
    }

    public double getClassAvg(){
        int size = grades.size();
        for(int index = 0; index < size; index++) {
            total += grades.index;
        }
        total = total/size;

        if(testscore >= 90)
        {
            return 4;
        }
        else if (testscore >= 80)
        {
            return 3;
        }
        else if (testscore >= 70)
        {
            return 2;
        }
        else
        {
            return 1;
        }

    }
}
