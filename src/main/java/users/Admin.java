package users;

import security.Hash;

/**
 * Entity class that represents an admin user.
 */
public class Admin extends User {
    public Admin(String userName, Hash password) {
        super(userName, password);
    }
}
