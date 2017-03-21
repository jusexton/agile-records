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

static Course generateCourse() {
    // TODO: Randomly generate course instance with realistic values.
}

static Grade generateGrade() {
    // Get random GradeType value.
    GradeType[] gradeTypes = GradeType.values()
    GradeType type = gradeTypes[getRandomInt(0, gradeTypes.length)]
    // TODO: Randomly generate grade instance.
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
        // Add count number of students.
        count.times {
            connection.addUser(generateStudent())
        }
    } catch (SQLException ex) {
        ex.printStackTrace()
    }
}

// Places 5 randomly generated users in database.
// populateDatabase(5)
