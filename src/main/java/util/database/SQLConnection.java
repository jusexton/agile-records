package main.java.util.database;

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
 * Holds tools used for reading and writing to a specified database.
 */
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

    public int getNextID(){

        int nextID;
        String studentIDQuery = "SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'students'";
        String adminIDQuery = "SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'administrators'";

        try {

            // create the mysql insert preparedstatement
            Statement st1 = connection.createStatement();

            ResultSet rs1 = st1.executeQuery(studentIDQuery);

            int nextStudentID = -1;

            rs1.first();
            nextStudentID =  rs1.getInt("auto_increment");

            Statement st2 = connection.createStatement();

            ResultSet rs2 = st2.executeQuery(adminIDQuery);

            int nextAdminID = -1;

            rs2.first();
            nextAdminID =  rs2.getInt("auto_increment");

            nextID = Math.max(nextStudentID,nextAdminID);


        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return -1;
        }


        return nextID;
    }


    public void addUser(User newUser){

        Gson gsonData = new GsonBuilder().create();
        String userData  = gsonData.toJson(newUser);

        String query;

        if(newUser instanceof Student){

            query = "INSERT INTO `txscypaa_agilerecords`.`students` (`id`,`username`, `studentData`) VALUES  (?,?,?)";

        }
        else {
            query = "INSERT INTO `txscypaa_agilerecords`.`administrators` (`id`, `username`,`adminData`) VALUES  (?,?,?)";
        }


        try {

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);

           // preparedStmt.setString (1, "");
            preparedStmt.setInt(1,getNextID());
            preparedStmt.setString (2, newUser.getUserName());
            preparedStmt.setString (3, userData);

            // execute the preparedstatement
            preparedStmt.execute();


        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            //return;
        }


    }

    public Student getStudent(int id){

        try {

            String query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  `id` ='" + id + "'";

            // create the mysql insert preparedstatement
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            String studentData = "";

            rs.first();
            studentData =  rs.getString("studentData");

            Gson gsonData = new GsonBuilder().create();
            Student theStudent = gsonData.fromJson(studentData,Student.class);

            //System.out.println(studentData);

            return theStudent;


        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }

    }

    public User getUser(int id){

        String query;

        try {

            query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  `id` ='" + id + "'";

            // create the mysql insert preparedstatement
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            String studentData = "";

            if(rs.first()) {
                studentData = rs.getString("studentData");

                Gson gsonData = new GsonBuilder().create();
                Student theStudent = gsonData.fromJson(studentData, Student.class);

                //System.out.println(studentData);

                return theStudent;
            }
            else {

                query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`administrators`  WHERE  `id` ='" + id + "'";

                String adminData = "";

                if (rs.first()) {
                    adminData = rs.getString("adminData");

                    Gson gsonData = new GsonBuilder().create();
                    Admin theAdmin = gsonData.fromJson(adminData, Admin.class);

                    //System.out.println(studentData);

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

    public List<Student> getAllStudents(){

        List<Student> allStudents = new ArrayList<>();

        try {

            String query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  1";

            // create the mysql insert preparedstatement
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            String studentData = "";

            while(rs.next()) {

                studentData = rs.getString("studentData");
                Gson gsonData = new GsonBuilder().create();
                Student theStudent = gsonData.fromJson(studentData,Student.class);
               // System.out.println(studentData);
                allStudents.add(theStudent);
            }
            return allStudents;


        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }

    }

    public boolean updateStudent(int id, Student student){


        try {

            Gson gsonData = new GsonBuilder().create();
            String studentData  = gsonData.toJson(student);

           // System.out.println(studentData);


            String query = "UPDATE `txscypaa_agilerecords`.`students` SET `studentData` = ? WHERE `students`.`id` = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);

            // preparedStmt.setString (1, "");
            preparedStmt.setString (1, studentData);
            preparedStmt.setInt (2, id);

            // execute the preparedstatement
            if(preparedStmt.execute())
                return true;


        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /*

        TODO : Is Username Unique

        public boolean isUniqueUsername(String username)

     */

    public User doLogin(String username, String password){


        try {

            String query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  `username` ='" + username + "'";

            // create the mysql insert preparedstatement
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            String studentData = "";

            if(rs.first()){


                studentData =  rs.getString("studentData");

                Gson gsonData = new GsonBuilder().create();
                Student theStudent = gsonData.fromJson(studentData,Student.class);

                Hash hash = theStudent.getPassword();



                try{


                    if(hash.equals(HashingUtil.hash(password,hash.getAlgorithm(),hash.getSalt())))
                    {
                        return theStudent;
                    }


                }
                catch (NoSuchAlgorithmException e)
                {
                    e.printStackTrace();
                }

            }

            return null;


        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }



    }

    /*

     TODO: Login Method

     public User doLogin(String username, String password)

     password needs to be hashed and compared to the hash in the DB

     getSalt

     Go until you find the username

     add username field to database

      */

    public Connection establishConnection() {
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
