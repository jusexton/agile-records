package main.users.students

import users.students.Course
import users.students.Grade
import users.students.GradeType
import time.DateInterval
import time.TimeInterval
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

class CourseTests extends GroovyTestCase {
    static Course getTestCourse() {
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

        return testCourse
    }

    // Passed
    void testCourseAverage() {
        assertEquals(getTestCourse().getAverage(), 4)
    }

    // Passed
    void testDateInterval() {
        DateInterval dateInterval = getTestCourse().getDateInterval()

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
    void testTimeInterval() {
        TimeInterval timeInterval = getTestCourse().getTimeInterval()

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
    static void parseTest() {
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
