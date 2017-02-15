package main.java.users;

import main.java.util.security.Hash;

/**
 * Class that will represent all users that reside on sql server.
 */
public abstract class User {
    private int ID;
    private String userName;
    private Hash password;
    private String firstName;
    private String lastName;
    private String email;

    public User(String userName, Hash password, int ID) {
        this.userName = userName;
        this.password = password;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Hash getPassword() {
        return password;
    }

    public void setPassword(Hash hash) {
        this.password = hash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
