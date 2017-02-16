package main.java.users.students;

import main.java.users.User;
import main.java.util.security.Hash;

/**
 * Class that represents students on the sql server.
 */
public class Student extends User {
    public Student(String userName, Hash password, int ID) {
        super(userName, password, ID);
    }
}
