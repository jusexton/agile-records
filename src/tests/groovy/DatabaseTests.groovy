package tests.groovy

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import main.java.users.Admin
import main.java.users.students.Course
import main.java.users.students.Major
import main.java.users.students.Student
import main.java.util.database.SQLConnection
import main.java.util.security.Hash
import main.java.util.security.HashingUtil

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection

/**
 * Contains all tests involving the database
 */
class DatabaseTests extends GroovyTestCase {

    final String host = "jdbc:mysql://gator4196.hostgator.com:3306/txscypaa_agilerecords"
    final String password = "txscypaa_agile"
    final String dbName = "4@lq^tsFiI0b"

    Student createTestStudent() {

        Hash hash  = HashingUtil.hash("123456","SHA-256")
        Student testStudent = new Student("mschultz", hash);

        List courses = new ArrayList();
        courses.add(new Course("CS 3420", 3, 10426));
        courses.add(new Course("CS 3306", 3, 10515));
        courses.add(new Course("CS 2410", 4, 10818));

        testStudent.setCourses(courses);

        testStudent.setMajor(Major.ComputerScience);

        testStudent.setEmail("mike@schultz.ms");

        testStudent.setFirstName("Mike");
        testStudent.setLastName("Schultz");
        testStudent.setID(999);

        return testStudent;

    }

    Admin createTestAdmin() {

        Admin testAdmin = new Admin("adminuser", new Hash("111111", "222222", "333333"));


        testAdmin.setEmail("admin@schultz.ms");

        testAdmin.setFirstName("Admin");
        testAdmin.setLastName("User");
        testAdmin.setID(123);

        return testAdmin;

    }


    public void getConnection() {


        Connection connection = SQLConnection.establishConnection(host, password, dbName)
        assertNotNull(connection)

    }

    void testAddStudent() {

        SQLConnection testConnection = new SQLConnection(host, password, dbName);

        testConnection.addUser(createTestStudent())

    }

    void testAddAdmin() {

        SQLConnection testConnection = new SQLConnection(host, password, dbName);

        testConnection.addUser(createTestAdmin())

    }

    void testGetStudent() {

        SQLConnection testConnection = new SQLConnection(host, password, dbName);

        testConnection.getStudent(24);

    }

    void testGetNextID() {

        SQLConnection testConnection = new SQLConnection(host, password, dbName);

        int id =
        testConnection.getNextID();

        System.out.println(id)


    }

    void testGetUser() {

        SQLConnection testConnection = new SQLConnection(host, password, dbName);

        testConnection.getUser(24);


    }



    void testGetAllStudents() {

        SQLConnection testConnection = new SQLConnection(host, password, dbName);

        List<Student> allStudents = testConnection.getAllStudents();

        assertEquals(allStudents.size(), 3)

    }


    void testUpdateStudent() {
        SQLConnection testConnection = new SQLConnection(host, password, dbName);

        testConnection.updateStudent(24, createTestStudent());

    }
    void testAddUsers(){

        SQLConnection testConnection = new SQLConnection(host, password, dbName);

        testConnection.addUser(createTestStudent())
       // testConnection.addUser(createTestAdmin())
    }

    void testDoLogin(){

        SQLConnection testConnection = new SQLConnection(host, password, dbName);

        Student theStudent = testConnection.doLogin("mschultz","123456")

        System.out.println(theStudent.getFirstName())



    }
}