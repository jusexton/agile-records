package tests.groovy

import main.java.users.students.Course
import main.java.users.students.Grade
import main.java.users.students.GradeType

/**
 * Contains all course tests.
 */
class CourseTests extends GroovyTestCase{
    static Course getTestCourse() {
        Course testCourse = new Course("Math 2302", 4, 12345)

        Grade gradeOne = new Grade(90, GradeType.Test)
        Grade gradeTwo = new Grade(90, GradeType.Test)

        testCourse.getGrades().addAll(gradeOne, gradeTwo)

        return testCourse
    }

    void testCourseSerializationPerformance() {

    }

    void testCourseDeserializationPerformance() {

    }
}
