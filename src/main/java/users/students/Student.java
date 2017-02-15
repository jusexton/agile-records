package main.java.users.students;

import main.java.users.User;

/**
 * Class that represents students on the sql server.
 */
public class Student extends User {
    public Student(String userName, String password, int ID) {
        super(userName, password, ID);
    }
}
