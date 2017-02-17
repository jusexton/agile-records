package main.java.users;

import main.java.util.security.Hash;

/**
 * Represents an admin user on the sql server.
 */
public class Admin extends User {

    public Admin(String userName, Hash password) {
        super(userName, password);
    }
}
