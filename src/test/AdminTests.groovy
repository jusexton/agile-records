package test

import main.java.users.Admin
import main.java.users.students.Student
import main.java.security.Hash
import main.java.security.util.HashingUtil

/**
 * Class that will contain all tests regarding the Admin class.
 */
class AdminTests extends GroovyTestCase {
    static Admin getTestAdmin(){
        Hash hash = HashingUtil.hash("username", "SHA-256")
        return new Admin("admin", hash)
    }

    // Passed
    void testInstance(){
        Admin testAdmin = getTestAdmin()
        assertTrue(testAdmin instanceof Admin)
        assertFalse(testAdmin instanceof Student)
    }
}
