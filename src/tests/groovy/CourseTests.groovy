package tests.groovy

import main.java.users.students.Course
import main.java.users.students.Grade
import main.java.users.students.GradeType
import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Contains all course tests.
 */
@RunWith(JUnit4.class)
class CourseTests extends GroovyTestCase {
    static Course testCourse

    @BeforeClass
    static void createTestCourse() {
        Course testCourse = new Course("Math 2302", 4, 12345)

        Grade gradeOne = new Grade(90, GradeType.Test)
        Grade gradeTwo = new Grade(90, GradeType.Test)
        testCourse.getGrades().addAll(gradeOne, gradeTwo)

        // Dates
        DateTime startDate = new DateTime(2017, 1, 21, 0, 0, 0)
        DateTime endDate = new DateTime(2017, 5, 21, 0, 0, 0)

        // Times
        DateTimeFormatter formatter = DateTimeFormat.forPattern("h:mma")
        DateTime startTime = formatter.parseDateTime("4:00PM")
        DateTime endTime = formatter.parseDateTime("5:45PM")

        // Intervals
        testCourse.setDateInterval(new Interval(startDate, endDate))
        testCourse.setTimeInterval(new Interval(startTime, endTime))

        this.testCourse = testCourse
    }

    // Passed
    @Test
    void testCourseAverage() {
        assertEquals(testCourse.getAverage(), 4)
    }

    // Passed
    @Test
    void testDateInterval() {
        Interval dateInterval = testCourse.getDateInterval()

        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy")
        DateTime start = dateInterval.getStart()
        assertEquals(start.getYear(), 2017)
        assertEquals(start.getMonthOfYear(), 1)
        assertEquals(start.getDayOfMonth(), 21)
        assertEquals(formatter.print(start), "01-21-2017")

        DateTime end = dateInterval.getEnd()
        assertEquals(end.getYear(), 2017)
        assertEquals(end.getMonthOfYear(), 5)
        assertEquals(end.getDayOfMonth(), 21)
        assertEquals(formatter.print(end), "05-21-2017")
    }

    // Passed
    @Test
    void testTimeInterval() {
        Interval timeInterval = testCourse.getTimeInterval()

        DateTimeFormatter formatter = DateTimeFormat.forPattern("h:mma")
        DateTime start = timeInterval.getStart()
        assertEquals(start.getHourOfDay(), 16)
        assertEquals(start.getMinuteOfHour(), 0)
        assertEquals(formatter.print(start), "4:00PM")

        DateTime end = timeInterval.getEnd()
        assertEquals(end.getHourOfDay(), 17)
        assertEquals(end.getMinuteOfHour(), 45)
        assertEquals(formatter.print(end), "5:45PM")
    }
}
