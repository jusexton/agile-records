package main.java.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.users.Admin;
import main.java.users.User;
import main.java.users.students.Student;
import main.java.util.security.Hash;
import main.java.util.security.HashingUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a connection to a sql database.
 */
// TODO: Clean up code, optimize. NOTE: Functions with written documentation will be considered clean and stable.
public class SQLConnection {
    private Connection connection;
    private String host;
    private String password;
    private String dbName;

    public SQLConnection(String host, String password, String dbName) {
        this.host = host;
        this.password = password;
        this.dbName = dbName;

        this.connection = establishConnection(host, password, dbName);
    }

    /**
     * Use to obtain an sql connection.
     * NOTE: This function is temporary.
     * WARNING: MAY RETURN NULL
     *
     * @return The sql connection.
     */
    public static Connection establishConnection(final String host,
                                                 final String password,
                                                 final String dbName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("No MySQL JDBC Driver Detected!");
            e.printStackTrace();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(host, password, dbName);
        } catch (SQLException e) {
            System.err.println("Connection Failed!");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Determines the next unique ID in the database.
     *
     * @return The next unique ID.
     */
    // TODO: Determine if we need to check for both ID's being -1.
    public int getNextID() {
        int nextStudentID = getAutoIncrement("students");
        int nextAdminID = getAutoIncrement("administrators");
        return Math.max(nextStudentID, nextAdminID);
    }

    /**
     * Helper function for getNextID, given a table query.
     *
     * @param tableName The name of the table that will be access for its auto increment.
     * @return The next id available.
     */
    private int getAutoIncrement(String tableName) {
        int nextID;
        String query = String.format(
                "SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES WHERE table_name = '%s'",
                tableName);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.first();
            nextID = resultSet.getInt("auto_increment");
        } catch (SQLException e) {
            nextID = -1;
        }
        return nextID;
    }


    public void addUser(User newUser) {
        Gson gson = new GsonBuilder().create();
        String userData = gson.toJson(newUser);

        String query;
        if (newUser instanceof Student) {
            query = "INSERT INTO `txscypaa_agilerecords`.`students` (`id`,`username`, `studentData`) VALUES  (?,?,?)";
        } else {
            query = "INSERT INTO `txscypaa_agilerecords`.`administrators` (`id`, `username`,`adminData`) VALUES  (?,?,?)";
        }

        try {
            // create the mysql insert prepared statement
            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt(1, getNextID());
            preparedStmt.setString(2, newUser.getUserName());
            preparedStmt.setString(3, userData);

            preparedStmt.execute();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }

    public Student getStudent(int id) {
        Student student = null;
        try {
            String query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  `id` ='" + id + "'";

            // create the mysql insert prepared statement
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.first()){
                String studentData = rs.getString("studentData");

                Gson gson = new GsonBuilder().create();
                student = gson.fromJson(studentData, Student.class);
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return student;
    }

    public User getUser(int id) {
        try {
            String query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  `id` ='" + id + "'";

            // create the mysql insert prepared statement
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.first()) {
                String studentData = rs.getString("studentData");
                Gson gson = new GsonBuilder().create();
                //System.out.println(studentData);
                return gson.fromJson(studentData, Student.class);
            } else {
                // TODO: This query is never used, handle accordingly.
                query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`administrators`  WHERE  `id` ='" + id + "'";

                if (rs.first()) {
                    String adminData = rs.getString("adminData");

                    Gson gson = new GsonBuilder().create();
                    Admin theAdmin = gson.fromJson(adminData, Admin.class);
                    return theAdmin;
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> allStudents = new ArrayList<>();

        try {
            String query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  1";

            // create the mysql insert prepared statement
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            Gson gson = new GsonBuilder().create();
            while (rs.next()) {
                String studentData = rs.getString("studentData");
                allStudents.add(gson.fromJson(studentData, Student.class));
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return allStudents;
    }

    public boolean updateStudent(int id, Student student) {
        try {
            Gson gson = new GsonBuilder().create();
            String studentData = gson.toJson(student);

            String query = "UPDATE `txscypaa_agilerecords`.`students` SET `studentData` = ? WHERE `students`.`id` = ?";

            // create the mysql insert prepared statement
            PreparedStatement preparedStmt = connection.prepareStatement(query);

            // preparedStmt.setString (1, "");
            preparedStmt.setString(1, studentData);
            preparedStmt.setInt(2, id);

            // execute the prepared statement
            return preparedStmt.execute();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return false;
    }

    /*
        TODO : Is Username Unique

        public boolean isUniqueUsername(String username)
     */

    // TODO: Update function to be able to login administrators aswell.
    public User attemptLogin(String username, String password) {
        try {
            String query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  `username` ='" + username + "'";

            // create the mysql insert prepared statement
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.first()) {
                String studentData = rs.getString("studentData");
                Gson gson = new GsonBuilder().create();
                Student theStudent = gson.fromJson(studentData, Student.class);

                Hash hash = theStudent.getPassword();
                try {
                    if (hash.equals(HashingUtil.hash(password, hash.getAlgorithm(), hash.getSalt()))) {
                        return theStudent;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return null;
    }

    /*

     TODO: Login Method

     public User attemptLogin(String username, String password)

     password needs to be hashed and compared to the hash in the DB

     getSalt

     Go until you find the username

     add username field to database

      */

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
