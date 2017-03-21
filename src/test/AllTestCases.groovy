package test

import junit.framework.Test
import script.DatabasePopulator
import test.main.AdminTests
import test.main.CourseTests
import test.main.DatabaseTests
import test.main.HashingTests
import test.main.StudentTests
import test.script.DatabasePopulatorTests

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
