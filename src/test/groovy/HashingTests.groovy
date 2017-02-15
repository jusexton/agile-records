package test.groovy

import main.java.util.security.HashingUtil

import java.util.stream.Stream

/**
 * Contains all hashing tests
 */
class HashingTests extends GroovyTestCase {

    /**
     * Tests that the salt generated is the specified
     * length.
     *
     * STATUS: PASS
     */
    void testSaltLength() {
        def length = 20
        String salt = HashingUtil.generateSalt(length, "abc", new Random())
        assertLength(length, salt.toCharArray())
    }

    /**
     * Tests that the salt only contains characters specified.
     *
     * STATUS: PASS
     */
    void testSaltCharset() {
        String charset = "abc"
        String salt = HashingUtil.generateSalt(20, charset, new Random())
        assertTrue salt.matches("^[" + charset + "_]+")
    }

    /**
     * Tests that the salts generated are random every time
     * the method is called.
     *
     * STATUS: PASS
     */
    void testSaltRandomness() {
        final limiter = 1000
        // Generates 1000 salts, none of which should be filtered
        // out with the distinct() call.
        def saltCount = Stream.generate { HashingUtil.generateSalt(20, "abc", new Random()) }
                .limit(limiter)
                .distinct()
                .count()
        // salts.forEach {it -> println(it)}
        assertEquals(limiter, saltCount)
    }

    /**
     * Tests that the Hash class's overrided function are
     * working as expected.
     *
     * STATUS: PASS
     */
    void testHashOverrides(){
        String password = "test_password"
        def hash = HashingUtil.hash(password, "SHA-256")
        // Test .toString()
        assertEquals(hash.toString(), hash.getHash())

        // Tests .equals()
        assertTrue(hash == HashingUtil.hash(password, "SHA-256"))
    }
}
