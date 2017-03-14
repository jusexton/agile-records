package tests.groovy

import main.java.database.FailedLoginException
import main.java.database.SQLConnection
import main.java.users.Admin
import main.java.users.User
import main.java.users.students.*
import main.java.security.Hash
import main.java.security.util.HashingUtil
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Contains all tests involving the database
 */
@RunWith(JUnit4.class)
class DatabaseTests extends GroovyTestCase {
    static SQLConnection testConnection
    static Student testStudent
    static Admin testAdmin

    // Executed before entering testing phase.
    @BeforeClass
    static void initialSetUp() {
        testConnection = new SQLConnection()
    }

    @Before
    void createTestStudent() {
        Hash hash = HashingUtil.hash("123456", "SHA-512")
        Student testStudent = new Student("mschultz", hash)

        //CS 3420 Grades
        Course cs3420 = new Course("CS 3420", 4, 10426)

        List cs3420Grades = new ArrayList()
        cs3420Grades.add(new Grade(100, GradeType.Homework))
        cs3420Grades.add(new Grade(90, GradeType.Homework))
        cs3420Grades.add(new Grade(85, GradeType.Homework))
        cs3420Grades.add(new Grade(72, GradeType.Midterm))
        cs3420Grades.add(new Grade(90, GradeType.Test))
        cs3420Grades.add(new Grade(80, GradeType.Test))
        cs3420Grades.add(new Grade(88, GradeType.Final))
        cs3420Grades.add(new Grade(94, GradeType.Project))

        //CS 3306 Grades
        Course cs3306 = new Course("CS 3306", 3, 10515)

        List cs3306Grades = new ArrayList()
        cs3306Grades.add(new Grade(90, GradeType.Homework))
        cs3306Grades.add(new Grade(80, GradeType.Homework))
        cs3306Grades.add(new Grade(75, GradeType.Homework))
        cs3306Grades.add(new Grade(90, GradeType.Midterm))
        cs3306Grades.add(new Grade(60, GradeType.Test))
        cs3306Grades.add(new Grade(66, GradeType.Test))
        cs3306Grades.add(new Grade(87, GradeType.Final))
        cs3306Grades.add(new Grade(95, GradeType.Project))

        //CS 2410 Grades
        Course cs2410 = new Course("CS 2410", 4, 10818)

        List cs2410Grades = new ArrayList()
        cs2410Grades.add(new Grade(20, GradeType.Homework))
        cs2410Grades.add(new Grade(40, GradeType.Homework))
        cs2410Grades.add(new Grade(60, GradeType.Homework))
        cs2410Grades.add(new Grade(80, GradeType.Midterm))
        cs2410Grades.add(new Grade(100, GradeType.Test))
        cs2410Grades.add(new Grade(80, GradeType.Test))
        cs2410Grades.add(new Grade(60, GradeType.Final))
        cs2410Grades.add(new Grade(40, GradeType.Project))

        cs3420.setGrades(cs3420Grades)
        cs3306.setGrades(cs3306Grades)
        cs2410.setGrades(cs2410Grades)

        List courses = new ArrayList()
        courses.add(cs3420)
        courses.add(cs3306)
        courses.add(cs2410)

        testStudent.setCourses(courses)
        testStudent.setMajor(Major.ComputerScience)
        testStudent.setEmail("mike@schultz.ms")
        testStudent.setFirstName("Mike")
        testStudent.setLastName("Schultz")
        testStudent.setID(999)

        this.testStudent = testStudent
    }

    @Before
    void createTestAdmin() {
        Hash hash = HashingUtil.hash("123456", "SHA-512")
        Admin testAdmin = new Admin("adminuser", hash)
        testAdmin.setEmail("admin@schultz.ms")
        testAdmin.setFirstName("Admin")
        testAdmin.setLastName("User")
        testAdmin.setID(123)

        this.testAdmin = testAdmin
    }

    // Passed
    @Test
    void testEstablishConnection() {
        assertNotNull(testConnection)
    }

    // Passed
    // WARNING: Make sure the username is unique before running test.
    @Test
    void testAddAdmin() {
        testAdmin.setUserName("admin")
        Admin result = (Admin) testConnection.addUser(testAdmin)
        assertNotNull(result)
    }

    // Passed
    // WARNING: Make sure the username is unique before running test.
    @Test
    void testAddStudent() {
        testStudent.setUserName("student")
        Student result = (Student) testConnection.addUser(testStudent)
        assertNotNull(result)
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
    void testGetUserById() {
        User user = testConnection.getUserById(61)
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

    // Passed
    @Test
    void testUpdateUser() {
        testStudent.setUserName("testStudent")
        testConnection.addUser(testStudent)
        testStudent.setUserName("testStudent2")
        testStudent.lastName = "Sexton"
        assertTrue(testConnection.updateUser(testStudent))
    }

    // Passed
    // WARNING: Test may change frequently.
    @Test
    void testRemoveStudent() {
        assertTrue(testConnection.removeUserById(46))
    }

    // Passed
    // WARNING: Test may change frequently.
    @Test
    void testRemoveAdmin() {
        assertTrue(testConnection.removeUserById(60))
    }
}