package main.util

import users.students.Major
import util.StringUtil

/**
 */
class StringUtilTests extends GroovyTestCase {
    void testFormatMajor(){
        assertEquals("Accounting", StringUtil.formatMajor(Major.Accounting))
        assertEquals("Computer Science", StringUtil.formatMajor(Major.ComputerScience))
        assertEquals("Software Engineering", StringUtil.formatMajor(Major.SoftwareEngineering))
    }
}
