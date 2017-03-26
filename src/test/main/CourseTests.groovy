package test.main

import main.java.users.students.Course
import main.java.users.students.Grade
import main.java.users.students.GradeType
import main.java.time.DateInterval
import main.java.time.TimeInterval
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Contains all course tests.
 */
@RunWith(JUnit4.class)
class CourseTests extends GroovyTestCase {
    static Course testCourse

    @BeforeClass
    static void createTestCourse() {
        Course testCourse = new Course("MathUtil 2302", 4, 12345)

        Grade gradeOne = new Grade(90, GradeType.Test)
        Grade gradeTwo = new Grade(90, GradeType.Test)
        testCourse.getGrades().addAll(gradeOne, gradeTwo)

        // Dates
        LocalDate startDate = LocalDate.of(2017, 1, 21)
        LocalDate endDate = LocalDate.of(2017, 5, 21)

        // Times
        LocalTime startTime = LocalTime.of(16, 0, 0)
        LocalTime endTime = LocalTime.of(17, 45, 0)

        // Intervals
        testCourse.setDateInterval(new DateInterval(startDate, endDate))
        testCourse.setTimeInterval(new TimeInterval(startTime, endTime))

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
        DateInterval dateInterval = testCourse.getDateInterval()

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        LocalDate start = dateInterval.getStart()
        assertEquals(start.getYear(), 2017)
        assertEquals(start.getMonthValue(), 1)
        assertEquals(start.getDayOfMonth(), 21)
        assertEquals(start.format(formatter), "01-21-2017")

        LocalDate end = dateInterval.getEnd()
        assertEquals(end.getYear(), 2017)
        assertEquals(end.getMonthValue(), 5)
        assertEquals(end.getDayOfMonth(), 21)
        assertEquals(end.format(formatter), "05-21-2017")
    }

    // Passed
    @Test
    void testTimeInterval() {
        TimeInterval timeInterval = testCourse.getTimeInterval()

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma")
        LocalTime start = timeInterval.getStart()
        assertEquals(start.getHour(), 16)
        assertEquals(start.getMinute(), 0)
        assertEquals(start.format(formatter), "4:00PM")

        LocalTime end = timeInterval.getEnd()
        assertEquals(end.getHour(), 17)
        assertEquals(end.getMinute(), 45)
        assertEquals(end.format(formatter), "5:45PM")
    }

    // Passed
    @Test
    void parseTest() {
        Exception ex = null
        String time = "4:00PM"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma")
        try {
            LocalTime.parse(time, formatter)
        } catch (DateTimeParseException e) {
            ex = e
        }
        assertNull(ex)
    }
}
