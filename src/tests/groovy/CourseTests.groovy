package tests.groovy

import main.java.users.students.Course
import main.java.users.students.Grade
import main.java.users.students.GradeType
import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

/**
 * Contains all course tests.
 */
class CourseTests extends GroovyTestCase{
    static Course getTestCourse() {
        Course testCourse = new Course("Math 2302", 4, 12345)

        Grade gradeOne = new Grade(90, GradeType.Test)
        Grade gradeTwo = new Grade(90, GradeType.Test)

        testCourse.getGrades().addAll(gradeOne, gradeTwo)

        // Dates
        DateTime startDate = new DateTime(2017, 1, 21, 0, 0, 0)
        DateTime endDate = new DateTime(2017, 5, 21, 0, 0, 0)

        // Times
        DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mma")
        DateTime startTime = formatter.parseDateTime("4:00pm")
        DateTime endTime = formatter.parseDateTime("5:45pm")

        // Intervals
        testCourse.setDateInterval(new Interval(startDate, endDate))
        testCourse.setTimeInterval(new Interval(startTime, endTime))

        return testCourse
    }

    // Passed
    void testCourseAverage(){
        assertEquals(getTestCourse().getAverage(), 4)
    }

    void testDateInterval(){
        Course testCourse = getTestCourse()
        assertEquals(testCourse.getDateInterval().getStart().getYear(), 2017)
        assertEquals(testCourse.getDateInterval().getStart().getMonthOfYear(), 1)
        assertEquals(testCourse.getDateInterval().getStart().getDayOfMonth(), 21)

        assertEquals(testCourse.getDateInterval().getEnd().getYear(), 2017)
        assertEquals(testCourse.getDateInterval().getEnd().getMonthOfYear(), 5)
        assertEquals(testCourse.getDateInterval().getEnd().getDayOfMonth(), 21)
    }

    void testTimeInterval(){
        // TODO: Test course time interval
    }
}
