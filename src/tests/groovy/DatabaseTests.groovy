package tests.groovy

import main.java.database.FailedLoginException
import main.java.database.SQLConnection
import main.java.users.Admin
import main.java.users.User
import main.java.users.students.Course
import main.java.users.students.Major
import main.java.users.students.Student
import main.java.util.security.Hash
import main.java.util.security.HashingUtil
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import java.sql.Connection

/**
 * Contains all tests involving the database
 */
@RunWith(JUnit4.class)
class DatabaseTests extends GroovyTestCase {
    final static String host = "jdbc:mysql://gator4196.hostgator.com:3306/txscypaa_agilerecords"
    final static String password = "txscypaa_agile"
    final static String dbName = "4@lq^tsFiI0b"

    static SQLConnection testConnection

    // Executed before entering testing phase.
    @BeforeClass
    static void initialSetUp() {
        testConnection = new SQLConnection(host, password, dbName)
    }

    static Student createTestStudent() {
        Hash hash = HashingUtil.hash("123456", "SHA-256")
        Student testStudent = new Student("mschultz", hash)

        List courses = new ArrayList()
        courses.add(new Course("CS 3420", 3, 10426))
        courses.add(new Course("CS 3306", 3, 10515))
        courses.add(new Course("CS 2410", 4, 10818))

        testStudent.setCourses(courses)
        testStudent.setMajor(Major.ComputerScience)
        testStudent.setEmail("mike@schultz.ms")
        testStudent.setFirstName("Mike")
        testStudent.setLastName("Schultz")
        testStudent.setID(999)

        return testStudent
    }

    static Admin createTestAdmin() {
        Admin testAdmin = new Admin("adminuser", new Hash("111111", "222222", "333333"))
        testAdmin.setEmail("admin@schultz.ms")
        testAdmin.setFirstName("Admin")
        testAdmin.setLastName("User")
        testAdmin.setID(123)

        return testAdmin
    }

    // Passed
    @Test
    void testEstablishConnection() {
        Connection connection = SQLConnection.establishConnection(host, password, dbName)
        assertNotNull(connection)
    }

    // Passed
    // WARNING: Make sure the username is unique before running test.
    @Test
    void testAddStudent() {
        Admin testAdmin = createTestAdmin()
        testAdmin.setUserName("unique")
        boolean result = testConnection.addUser(testAdmin)
        assertTrue(result)
    }

    // Passed
    // WARNING: Test may change frequently.
    @Test
    void testIsUnique() {
        assertTrue(testConnection.isUnique("TestUsername"))
        assertFalse(testConnection.isUnique("adminuser"))
    }

    // Passed
    // WARNING: Test may change frequently.
    @Test
    void testGetNextAutoID() {
        assertEquals(testConnection.getNextAutoID("students"), 47)
        assertEquals(testConnection.getNextAutoID("administrators"), 51)
    }

    // Passed
    @Test
    void testGetUser() {
        User user = testConnection.getUser(50)
        assertNotNull(user)
        println(user.toString())
    }

    // Passed
    @Test
    void testGetAllUsers() {
        def allUsers = testConnection.getAllUsers()
        allUsers.entrySet().stream()
                .flatMap { entry -> entry.getValue().stream() }
                .map { user -> user.getUserName() }
                .forEach { username -> println(username) }
    }

    // Passed
    @Test
    void testCorrectLogin() {
        // Correct login credentials
        User user = testConnection.attemptLogin("mschultz", "123456")
        assertNotNull(user)
        assertTrue(user instanceof Student)
        assertFalse(user instanceof Admin)
    }

    // Passed
    @Test(expected = FailedLoginException.class)
    void testIncorrectPassword() {
        testConnection.attemptLogin("mschultz", "000000")
    }

    // Passed
    @Test(expected = FailedLoginException.class)
    void testIncorrectUsername() {
        testConnection.attemptLogin("Random-Username", "123456")
    }
}