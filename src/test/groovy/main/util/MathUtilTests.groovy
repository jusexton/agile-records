package main.util

import util.MathUtil

/**
 * Class containing all MathUtil tests.
 */
class MathUtilTests extends GroovyTestCase {
    // Passed
    void testGetRandomInt() {
        int min = 0
        int max = 10
        int randomInt = MathUtil.getRandomInt(min, max)
        // println(randomInt)
        assertTrue(randomInt >= min && randomInt <= max)
    }

    // Passed
    void testGetRandomDouble() {
        int min = 0.0
        int max = 100.5
        double randomDouble = MathUtil.getRandomDouble(min, max)
        // println(randomDouble)
        assertTrue(randomDouble >= min && randomDouble <= max)
    }

    // Passed
    void testRound() {
        double testValue = 56.7646
        assertEquals(56.8, MathUtil.round(testValue, 1))
        assertEquals(56.76, MathUtil.round(testValue, 2))
        assertEquals(56.765, MathUtil.round(testValue, 3))
        assertEquals(56.7646, MathUtil.round(testValue, 4))
    }

    // Passed
    void testIsInteger() {
        assertTrue(MathUtil.isInteger("5"))
        assertTrue(MathUtil.isInteger("-5"))
        assertFalse(MathUtil.isInteger("5.5"))
        assertFalse(MathUtil.isInteger("."))
        assertFalse(MathUtil.isInteger("7+6"))
    }

    // Passed
    void testIsDouble() {
        assertTrue(MathUtil.isDouble("5.5"))
        assertTrue(MathUtil.isDouble("5"))
        assertTrue(MathUtil.isDouble("-5"))
        assertTrue(MathUtil.isDouble("-5.5"))
        assertFalse(MathUtil.isDouble("."))
        assertFalse(MathUtil.isDouble("7+6"))
    }

    // Passed
    void testIsNumeric() {
        assertTrue(MathUtil.isNumeric("5"))
        assertTrue(MathUtil.isNumeric("5.5"))
        assertTrue(MathUtil.isNumeric("-5.5"))
        assertTrue(MathUtil.isNumeric("-5"))
        assertFalse(MathUtil.isNumeric("."))
        assertFalse(MathUtil.isNumeric("5+5"))
        assertFalse(MathUtil.isNumeric("a"))
    }
}
