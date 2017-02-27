package tests.groovy

import main.java.users.Admin
import main.java.users.students.Course
import main.java.users.students.Major
import main.java.users.students.Student
import main.java.database.SQLConnection
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
// TODO: Write more stable and consistent tests.
@RunWith(JUnit4.class)
class DatabaseTests extends GroovyTestCase {
    final static String host = "jdbc:mysql://gator4196.hostgator.com:3306/txscypaa_agilerecords"
    final static String password = "txscypaa_agile"
    final static String dbName = "4@lq^tsFiI0b"

    static SQLConnection testConnection

    @BeforeClass
    static void initialSetUp(){
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

    @Test
    void testEstablishConnection() {
        Connection connection = SQLConnection.establishConnection(host, password, dbName)
        assertNotNull(connection)
    }

    @Test
    void testAddStudent() {
        testConnection.addUser(createTestStudent())
    }

    @Test
    void testAddAdmin() {
        testConnection.addUser(createTestAdmin())
    }

    @Test
    void testGetNextID() {
        System.out.println(testConnection.getNextID())
    }

    @Test
    void testGetUser() {
        testConnection.getUser(24)
    }

    @Test
    void testGetAllUsers(){
        testConnection.getAllUsers().entrySet().forEach { key -> key.getValue().forEach { e -> println(e.getFirstName())} }
    }

    @Test
    void testGetAllStudents() {
        List<Student> allStudents = testConnection.getAllStudents()
        // WARNING: THIS ASSERT CHANGES FREQUENTLY
        // assertEquals(allStudents.size(), 3)
    }

    @Test
    void testUpdateStudent() {
        testConnection.updateStudent(24, createTestStudent())
    }

    @Test
    void testAddUser() {
        testConnection.addUser(createTestStudent())
        // testConnection.addUser(createTestAdmin())
    }

    @Test
    void testAttemptLogin() {
        Student theStudent = testConnection.attemptLogin("mschultz", "123456")
        println(theStudent.getFirstName())
        theStudent.getCourses().forEach {it -> println(it) }
    }
}