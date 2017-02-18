package tests.groovy

import junit.framework.Test

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
        return allTests
    }
}
