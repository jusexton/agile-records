package test.script

import main.java.users.students.Course
import main.java.users.students.Grade
import main.java.users.students.Student
import script.groovy.DatabasePopulator.Populator
import script.groovy.DatabasePopulator.MathUtil

/**
 */
class DatabasePopulatorTests extends GroovyTestCase {
    // Passed
    void testGetRandomInt() {
        int randomInt = MathUtil.getRandomInt(0, 100)
        // println(randomInt)
        assertTrue(randomInt >= 0 && randomInt <= 100)
    }

    // Passed
    void testGetRandomDouble() {
        double randomDouble = MathUtil.getRandomDouble(0, 100)
        // println(randomDouble)
        assertTrue(randomDouble >= 0 && randomDouble <= 100)

    }

    // Passed
    void testGenerateGrade() {
        Grade testGrade = Populator.generateGrade()
        // println(testGrade.getScore())
        assertTrue(testGrade.getScore() >= 50 && testGrade.getScore() <= 100)
    }

    // Passed
    void testGenerateCourse() {
        Course testCourse = Populator.generateCourse()
        def grades = testCourse.getGrades()
        // grades.forEach { grade -> println(grade.getScore()) }
        assertTrue(grades.size() >= 0 && grades.size() <= 10)
    }

    // FAIL
    void testGenerateStudent() {
        Student testStudent = Populator.generateStudent()
        def courses = testStudent.getCourses()
        // courses.forEach { course -> println(course.getName())}
        assertTrue(courses.size() >= 1 && courses.size() <= 5)
    }

    // Passed
    void testGetRandomElement() {
        def intList = [1, 2, 3, 4, 5, 6, 7, 8, 9]
        100.times {
            // println(Populator.getRandomElement(intList))
        }
    }
}
