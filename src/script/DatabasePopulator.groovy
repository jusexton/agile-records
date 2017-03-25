package script

import main.java.database.SQLConnection
import main.java.users.students.Course
import main.java.users.students.Grade
import main.java.users.students.GradeType
import main.java.users.students.Student

import java.sql.SQLException

/**
 * Script used to generate users with random associated data
 * and insert them into database.
 */

class Constants {
    final static String[] COURSE_NAMES = [
            "MATH 2301",
            "MATH 2302",
            "CS 3302",
            "PHYS 4301",
            "ENGL 3301"]
}


static int getRandomInt(int min, int max) {
    return Math.abs(new Random().nextInt() % max + min)
}

static double getRandomDouble(int min, int max) {
    return min + (max - min) * new Random().nextDouble()
}

static Student generateStudent() {
    // TODO: Use name dictionary to generate first and last name.
    // TODO: Use first and last name to determine username.
    // TODO: Randomly select major.
    // TODO: Add generated courses.
}

// Note: Does not assign a time a the generated course.
static Course generateCourse() {
    // Credit hours hard coded to 3 because all courses in
    // COURSE_NAMES have 3 credit hours.
    Course course = new Course(
            Constants.COURSE_NAMES[getRandomInt(0, Constants.COURSE_NAMES.length)],
            3,
            getRandomInt(10000, 99999))

    // Randomly adds 0-10 grades to the course
    int gradeCount = getRandomInt(0, 10)
    gradeCount.times {
        course.getGrades().add(generateGrade())
    }
    return course
}

// Note: Does not assign a name the generated grade.
static Grade generateGrade() {
    // Get random GradeType value.
    GradeType[] gradeTypes = GradeType.values()
    GradeType type = gradeTypes[getRandomInt(0, gradeTypes.length)]
    return new Grade(getRandomDouble(50, 100), type)
}

/**
 *
 *
 * @param count The number of users that will be generated and added
 * to the database.
 */
static void populateDatabase(int count) {
    try {
        SQLConnection connection = new SQLConnection()
        count.times {
            connection.addUser(generateStudent())
        }
    } catch (SQLException ex) {
        ex.printStackTrace()
    }
}

// Will place 5 randomly generated users in database.
// populateDatabase(5)
