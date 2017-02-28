package main.java.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.users.Admin;
import main.java.users.User;
import main.java.users.students.Student;
import main.java.util.security.Hash;
import main.java.util.security.HashingUtil;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: Implement multiple ways to access users if necessary. (ex. by id, by username)

/**
 * Represents a connection to a sql database.
 */
public class SQLConnection implements AutoCloseable {
    private Connection connection;
    private String host;
    private String password;
    private String databaseName;

    public SQLConnection(String host, String password, String databaseName) {
        this.setConnection(host, password, databaseName);
    }

    @Override
    public void close() throws SQLException{
        connection.close();
    }

    /**
     * Use to obtain an sql connection.
     * NOTE: This function is temporary.
     * WARNING: MAY RETURN NULL
     *
     * @return The sql connection.
     */
    public static Connection establishConnection(String host, String password, String dbName) {
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
        int nextStudentID = getNextAutoID("students");
        int nextAdminID = getNextAutoID("administrators");
        return Math.max(nextStudentID, nextAdminID);
    }

    /**
     * Helper function for getNextID, given a table query.
     * Returns -1 if the table does not exist.
     *
     * @param tableName The name of the table that will be access for its auto increment.
     * @return The next id available.
     */
    public int getNextAutoID(String tableName) {
        String query = String.format(
                "SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES WHERE table_name = '%s'",
                tableName);
        int nextID = -1;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                nextID = resultSet.getInt("auto_increment");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextID;
    }

    /**
     * Determines if a given username is unique on the SQL server.
     *
     * @param username The username that needs to be checked for uniqueness
     * @return Whether the username is unique or not.
     */
    public boolean isUnique(String username) {
        String result = getAllUsers().entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .map(User::getUserName)
                .filter(name -> name.equals(username))
                .findFirst()
                .orElse("");

        return result.equals("");
    }

    /**
     * Adds user data to the correct data depending on whether the user
     * is an Admin or Student.
     *
     * @param newUser The user instance that will be added.
     * @return A User object with the ID updated from the database.
     */
    public User addUser(User newUser) {
        // Make sure username is unique and not empty.
        String username = newUser.getUserName();
        if (!isUnique(username) || username.equals("")) {
            return null;
        }

        // Determines which table the user will be in and creates the query.
        String group;
        String type;
        if (newUser instanceof Student) {
            group = "students";
            type = "studentData";
        } else {
            group = "administrators";
            type = "adminData";
        }
        String query = String.format(
                "INSERT INTO `txscypaa_agilerecords`.`%s` (`id`, `username`,`%s`) VALUES  (?,?,?)",
                group,
                type);

        // Serializes userData into Json
        // Creates query statement will all needed data.
        // Inserts data into database.
        int ID = getNextID();
        newUser.setID(ID);
        Gson gson = new GsonBuilder().create();
        String userData = gson.toJson(newUser);
        try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
            preparedStmt.setInt(1, ID);
            preparedStmt.setString(2, username);
            preparedStmt.setString(3, userData);
            preparedStmt.execute();
            return newUser;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Helper function for getUser.
     *
     * @param query The query that will be performed.
     * @param data  The data field that will be accessed.
     * @param type  The data type of the retrieved data.
     * @return User retrieved with the given query.
     */
    private User getUserByQuery(String query, String data, Type type) {
        User user = null;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                Gson gson = new GsonBuilder().create();
                String userData = resultSet.getString(data);
                user = gson.fromJson(userData, type);
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Returns the user in the database with the matching given ID.
     *
     * @param id The ID that will be used to find the wanted user.
     * @return The found user object.
     */
    public User getUser(int id) {
        String query = String.format(
                "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  `id` = '%d'",
                id);
        User user = getUserByQuery(query, "studentData", Student.class);
        if (user != null) {
            return user;
        }

        query = String.format(
                "SELECT `adminData` FROM `txscypaa_agilerecords`.`administrators`  WHERE  `id` = '%d'",
                id);
        user = getUserByQuery(query, "adminData", Student.class);
        if (user != null) {
            return user;
        }

        return null;
    }

    /**
     * Helper function for getAllUsers.
     *
     * @param query      The query that will be performed.
     * @param wantedData The data field that will be accessed.
     * @param type       The data type of the retrieved data.
     * @return List of users retrieved with the given query.
     */
    private List<User> getAllByQuery(String query, String wantedData, Type type) {
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            Gson gson = new GsonBuilder().create();
            while (resultSet.next()) {
                String userData = resultSet.getString(wantedData);
                users.add(gson.fromJson(userData, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Collects all records from each table and organizes each user by which table they belong too.
     *
     * @return The hash map containing all users.
     */
    public HashMap<String, List<User>> getAllUsers() {
        HashMap<String, List<User>> allUsers = new HashMap<>();

        String query = "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`";
        allUsers.put("students", getAllByQuery(query, "studentData", Student.class));

        query = "SELECT `adminData` FROM `txscypaa_agilerecords`.`administrators`";
        allUsers.put("admins", getAllByQuery(query, "adminData", Admin.class));

        return allUsers;
    }

    // TODO: Look over updateUser logic for bugs and optimizations.
    // TODO: Unit test updateUser.
    public boolean updateUser(int id, User user) {
        Gson gson = new GsonBuilder().create();
        String userData = gson.toJson(user);

        String query;
        if (user instanceof Student) {
            query = "UPDATE `txscypaa_agilerecords`.`students` SET `studentData` = ? WHERE `students`.`id` = ?";
        } else {
            query = "UPDATE `txscypaa_agilerecords`.`administrators` SET `adminData` = ? WHERE `administrators`.`id` = ?";
        }

        try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
            preparedStmt.setString(1, userData);
            preparedStmt.setInt(2, id);

            return preparedStmt.execute();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns a user object given the user's username and if the password matches the stored
     * username's password.
     *
     * @param username The username that will be queried.
     * @param password The password that will be compared if there is a matching username.
     * @return The user object.
     * @throws FailedLoginException Thrown when
     */
    public User attemptLogin(String username, String password) throws FailedLoginException {
        String query = String.format(
                "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  `username` = '%s'",
                username);
        User user = loginByQuery(query, password, "studentData", Student.class);
        if (user != null) {
            return user;
        }

        query = String.format(
                "SELECT `adminData` FROM `txscypaa_agilerecords`.`administrators`  WHERE  `username` = '%s'",
                username);
        user = loginByQuery(query, password, "adminData", Admin.class);
        if (user != null) {
            return user;
        }

        throw new FailedLoginException("Login Failed");
    }

    /**
     * Helper function for attemptLogin.
     *
     * @param query      The query that will be performed.
     * @param password   The password that will be used to attempt login
     * @param wantedData The data field that will be accessed.
     * @param type       The data type of the retrieved data.
     * @return The user retrieved
     * @throws FailedLoginException Thrown when login fails
     */
    @Nullable
    private User loginByQuery(String query,
                              String password,
                              String wantedData,
                              Type type) throws FailedLoginException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            Gson gson = new GsonBuilder().create();
            if (resultSet.next()) {
                String userData = resultSet.getString(wantedData);
                User user = gson.fromJson(userData, type);

                if (checkPassword(user.getPassword(), password)) {
                    return user;
                } else {
                    throw new FailedLoginException("Login Failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Helper function for loginByQuery.
     * Checks to see if a given password is equal to a given hash.
     *
     * @param hash     The hash object that will be compared.
     * @param password The string password that will be compared.
     * @return Whether they were equal or not.
     */
    private boolean checkPassword(Hash hash, String password) {
        boolean pass = false;
        try {
            pass = hash.equals(HashingUtil.hash(password, hash.getAlgorithm(), hash.getSalt()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pass;
    }

    // TODO: Complete removeUser function.
    public boolean removeUser(User user) {

        int ID = user.getID();

        String query;
        if (user instanceof Student) {
            query = "DELETE FROM `txscypaa_agilerecords`.`students` WHERE `students`.`id` = ?";
        } else {
            query = "DELETE FROM `txscypaa_agilerecords`.`students` WHERE `students`.`id` = ?";
        }

        try {

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1   , ID);
            preparedStmt.execute();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void setConnection(String host, String password, String databaseName) {
        this.host = host;
        this.password = password;
        this.databaseName = databaseName;
        this.connection = establishConnection(host, password, databaseName);
    }

    public Connection getConnection() {
        return connection;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
