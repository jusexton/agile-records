package main.users

import users.Admin
import users.students.Student
import security.Hash
import security.util.HashingUtil

/**
 * Class containing all tests regarding the Admin class.
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
