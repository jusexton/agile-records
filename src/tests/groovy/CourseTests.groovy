package tests.groovy

import main.java.time.Interval
import main.java.users.students.Course
import main.java.users.students.Grade
import main.java.users.students.GradeType
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        LocalDateTime startDate = LocalDateTime.of(2017, 1, 21, 0, 0, 0)
        LocalDateTime endDate = LocalDateTime.of(2017, 5, 21, 0, 0, 0)

        // Times
        LocalDateTime startTime = LocalDateTime.of(0 ,1 ,1, 16, 0, 0)
        LocalDateTime endTime = LocalDateTime.of(0, 1, 1, 17, 45, 0)

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        LocalDateTime start = dateInterval.getStart()
        assertEquals(start.getYear(), 2017)
        assertEquals(start.getMonthValue(), 1)
        assertEquals(start.getDayOfMonth(), 21)
        assertEquals(start.format(formatter), "01-21-2017")

        LocalDateTime end = dateInterval.getEnd()
        assertEquals(end.getYear(), 2017)
        assertEquals(end.getMonthValue(), 5)
        assertEquals(end.getDayOfMonth(), 21)
        assertEquals(end.format(formatter), "05-21-2017")
    }

    // Passed
    @Test
    void testTimeInterval() {
        Interval timeInterval = testCourse.getTimeInterval()

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma")
        LocalDateTime start = timeInterval.getStart()
        assertEquals(start.getHour(), 16)
        assertEquals(start.getMinute(), 0)
        assertEquals(start.format(formatter), "4:00PM")

        LocalDateTime end = timeInterval.getEnd()
        assertEquals(end.getHour(), 17)
        assertEquals(end.getMinute(), 45)
        assertEquals(end.format(formatter), "5:45PM")
    }
}
