import junit.framework.Test
import main.users.AdminTests
import main.users.students.CourseTests
import main.database.DatabaseTests
import main.security.HashingTests
import main.users.students.StudentTests
import script.DatabasePopulatorTests

/**
 * Will act as an intersection point for all test classes.
 */
class AllTestCases {
    static Test suite(){
        def allTests = new GroovyTestSuite()
        allTests.addTestSuite(AdminTests.class)
        allTests.addTestSuite(StudentTests.class)
        allTests.addTestSuite(DatabaseTests.class)
        allTests.addTestSuite(CourseTests.class)
        allTests.addTestSuite(HashingTests.class)
        allTests.addTestSuite(DatabasePopulatorTests.class)
        return allTests
    }
}
