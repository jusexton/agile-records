package script

import main.java.database.SQLConnection
import main.java.users.students.Course
import main.java.users.students.Grade
import main.java.users.students.Student

import java.sql.SQLException

/**
 * Script used to generate users and insert them into
 * wanted database.
 */

Student generateStudent() {
    // TODO: Use name dictionary to generate first and last name.
    // TODO: Use first and last name to determine username.
    // TODO: Randomly select major.
    // TODO: Add generated courses.
}

Course generateCourse() {
    // TODO: Randomly generate course instance with realistic values.
}

Grade generateGrade() {
    // TODO: Randomly generate grade instance.
}

/**
 *
 *
 * @param count The number of users that will be generated and added
 * to the database.
 */
void populateDatabase(int count) {
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
populateDatabase(5)
