package users;

import security.Hash;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String lastLoginTime;

    public User() {

    }

    public User(String userName, Hash password) {
        this.userName = userName;
        this.password = password;
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

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        this.lastLoginTime = lastLoginTime.format(formatter);
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime){
        setLastLoginTime(lastLoginTime, "yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public String toString() {
        return userName;
    }
}
