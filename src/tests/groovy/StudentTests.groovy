package tests.groovy

import main.java.users.students.Course
import main.java.users.students.Grade
import main.java.users.students.GradeType
import main.java.users.students.Student
import main.java.util.security.HashingUtil

/**
 * Class that will contain all tests regarding the Student class.
 */
class StudentTests extends GroovyTestCase {
    static Student getTestStudent() {
        // Create test course one.
        Course courseOne = new Course("Math 2302", 4, 12345)
        courseOne.getGrades().add(new Grade(90, GradeType.Test))
        courseOne.getGrades().add(new Grade(85, GradeType.Test))

        // Create test course two.
        Course courseTwo = new Course("Math 2304", 4, 54321)
        courseTwo.getGrades().add(new Grade(100, GradeType.Test))
        courseTwo.getGrades().add(new Grade(95, GradeType.Test))

        // Add them to list.
        List<Course> courses = new ArrayList<>()
        courses.add(courseOne)
        courses.add(courseTwo)

        // Create test student and return the object.
        Student testStudent = new Student("testStudent", HashingUtil.hash("password", "SHA-256"))
        testStudent.setCourses(courses)
        return testStudent
    }

    void testGpaAverage() {
        Student testStudent = getTestStudent()
        assertEquals(testStudent.getGPA(), 3.5)
    }
}
