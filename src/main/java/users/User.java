package main.java.users;

/**
 * Class that will represent all users that reside on sql server.
 */
public abstract class User {
    private int ID;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    public User(String userName, String password, int ID) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
