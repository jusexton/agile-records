package main.java.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.users.Admin;
import main.java.users.User;
import main.java.users.students.Student;
import main.java.util.security.Hash;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a connection to a sql database.
 */
public class SQLConnection implements AutoCloseable {
    private Connection connection;
    private String host = "jdbc:mysql://gator4196.hostgator.com:3306/txscypaa_agilerecords";
    private String username = "txscypaa_agile";
    private String password = "4@lq^tsFiI0b";

    public SQLConnection() throws SQLException {
        this.setConnection(host, username, password);
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    /**
     * Used to obtain an sql connection.
     * WARNING: MAY RETURN NULL
     *
     * @return The sql connection object.
     */
    public static Connection establishConnection(String host, String username, String password) throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("No MySQL JDBC Driver Detected!");
            e.printStackTrace();
        }
        return DriverManager.getConnection(host, username, password);
    }

    /**
     * Determines the next unique ID in the database.
     *
     * @return The next unique ID.
     */
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
     * Adds user data to the correct table depending on whether the user
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
                "INSERT INTO `txscypaa_agilerecords`.`%s` (`id`, `username`, `password`, `%s`) VALUES  (?,?,?,?)",
                group,
                type);

        // Serializes userData into Json
        // Creates query statement will all needed data.
        // Inserts data into database.
        int ID = getNextID();
        newUser.setID(ID);
        Gson gson = new GsonBuilder().create();
        String userData = gson.toJson(newUser);
        String password = gson.toJson(newUser.getPassword());
        try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
            preparedStmt.setInt(1, ID);
            preparedStmt.setString(2, username);
            preparedStmt.setString(3, password);
            preparedStmt.setString(4, userData);
            preparedStmt.execute();
            return newUser;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Helper function for getUserById.
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
    public User getUserById(int id) {
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
        user = getUserByQuery(query, "adminData", Admin.class);
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

    /**
     * Updates a user object.
     * WARNING: This method does not allow for ID changes.
     *
     * @param user The new user instance that will be written to the database.
     * @return Whether the update was successful or not.
     */
    public boolean updateUser(User user) {
        Gson gson = new GsonBuilder().create();
        String userData = gson.toJson(user);

        String username = user.getUserName();
        Gson gsonPassword = new GsonBuilder().create();
        String password = gsonPassword.toJson(user.getPassword());

        int id = user.getID();

        String query;
        if (user instanceof Student) {
            query = "UPDATE `txscypaa_agilerecords`.`students` SET `username` = ? , `password` = ?,`studentData` = ? WHERE `students`.`id` = ?";
        } else {
            query = "UPDATE `txscypaa_agilerecords`.`administrators` SET `username` = ? , `password` = ?,`adminData` = ?  WHERE `administrators`.`id` = ?";
        }

        try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);
            preparedStmt.setString(3, userData);
            preparedStmt.setInt(4, id);
            preparedStmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns a user object, given the username if and only if the given password matches the stored
     * username's password in the database..
     *
     * @param username The username that will be queried.
     * @param password The username that will be compared if there is a matching username.
     * @return The user object.
     * @throws FailedLoginException Thrown when
     */
    public User attemptLogin(String username, String password) throws FailedLoginException {
        String query;

        if (validateCredentials("students", username, password)) {
            query = String.format(
                    "SELECT `studentData` FROM `txscypaa_agilerecords`.`students`  WHERE  `username` = '%s'",
                    username);
            return getUserByQuery(query, "studentData", Student.class);
        }

        if (validateCredentials("administrators", username, password)) {
            query = String.format(
                    "SELECT `adminData` FROM `txscypaa_agilerecords`.`administrators`  WHERE  `username` = '%s'",
                    username);
            return getUserByQuery(query, "adminData", Admin.class);
        }

        throw new FailedLoginException("Login Failed");
    }

    /**
     * Helper function for attemptLogin.
     *
     * @param table The table that will be checked
     * @param username The username that will be used to attempt login
     * @param password The password that will be used to attempt login
     * @return The user retrieved
     */
    private boolean validateCredentials(String table, String username, String password)
            throws FailedLoginException {
        String query = String.format(
                "SELECT `password` FROM `txscypaa_agilerecords`.`%1$s`  WHERE  `username` = '%2$s'",
                table,
                username);

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String passwordData = resultSet.getString("password");
                Gson gson = new GsonBuilder().create();
                Hash hash = gson.fromJson(passwordData, Hash.class);
                if (hash.checkPassword(password)) {
                    return true;
                } else {
                    throw new FailedLoginException("Incorrect Password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes user by id.
     *
     * @param id The id'd user that will be removed.
     * @return Whether the deletion was successful or not.
     */
    public boolean removeUserById(int id) {
        User user = getUserById(id);

        String group = user instanceof Student ? "students" : "administrators";
        String query = String.format(
                "DELETE FROM `txscypaa_agilerecords`.`%1$s` WHERE `%1$s`.`id` = ?",
                group);

        try {
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, id);
            preparedStmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setConnection(String host, String username, String password) throws SQLException{
        this.host = host;
        this.username = username;
        this.password = password;
        this.connection = establishConnection(host, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
