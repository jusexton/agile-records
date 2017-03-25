package test.script

import main.java.users.students.Course
import main.java.users.students.Grade
import script.DatabasePopulator

/**
 */
class DatabasePopulatorTests extends GroovyTestCase {
    // Passed
    void testGetRandomInt() {
        int randomInt = DatabasePopulator.getRandomInt(0, 100)
        // println(randomInt)
        assertTrue(randomInt >= 0 && randomInt <= 100)
    }

    // Passed
    void testGetRandomDouble() {
        double randomDouble = DatabasePopulator.getRandomDouble(0, 100)
        // println(randomDouble)
        assertTrue(randomDouble >= 0 && randomDouble <= 100)

    }

    // Passed
    void testGenerateGrade() {
        Grade testGrade = DatabasePopulator.generateGrade()
        // println(testGrade.getScore())
        assertTrue(testGrade.getScore() >= 50 && testGrade.getScore() <= 100)
    }

    // Passed
    void testGenerateCourse(){
        Course testCourse = DatabasePopulator.generateCourse()
        def grades = testCourse.getGrades()
        // grades.forEach { grade -> println(grade.getScore()) }
        assertTrue(grades.size() >= 0 && grades.size() <= 10)
    }
}
