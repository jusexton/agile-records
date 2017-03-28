package main.security

import security.util.HashingUtil

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
        String salt = HashingUtil.generateSalt(length, "abc")
        println(salt)
        assertLength(length, salt.toCharArray())
    }

    /**
     * Tests that the salt only contains characters specified.
     *
     * STATUS: PASS
     */
    void testSaltCharset() {
        String charset = "abc"
        String salt = HashingUtil.generateSalt(20, charset)
        assertTrue(salt.matches("^[" + charset + "_]+"))
    }

    /**
     * Tests that the salts generated are random every time
     * the method is called.
     *
     * STATUS: PASS
     */
    void testSaltRandomness() {
        final limiter = 1000
        // Generates 1000 salts, none of which should be
        // filtered out with the distinct() call.
        def saltCount = Stream.generate { HashingUtil.generateSalt(20, "abc") }
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
    void testHashOverrides() {
        String password = "test_password"
        def hash = HashingUtil.hash(password, "SHA-256")

        assertEquals(hash.toString(), hash.getHash())
        assertTrue(hash == HashingUtil.hash(password, "SHA-256"))
    }

    /**
     * Tests that the sameSalt function works as expected.
     *
     * STATUS: PASS
     */
    void testHashSameSalt() {
        String algorithm = "SHA-256"
        String salt = HashingUtil.generateSalt(20, "abc")
        def hashOne = HashingUtil.hash("test_password", algorithm, salt)
        def hashTwo = HashingUtil.hash("different_password", algorithm, salt)

        assertTrue(hashOne.sameSalt(hashTwo))
        assertTrue(hashOne != hashTwo)
    }

    /**
     * Tests a similar process that will take place when a user
     * attempts to login.
     *
     * STATUS: PASS
     */
    void testLoginProcess() {
        def failPassword = "wrong_password"
        def correctPassword = "correct_password"
        def hash = HashingUtil.hash(correctPassword, "SHA-512", "salt")

        assertTrue(hash != HashingUtil.hash(failPassword, "SHA-512", "salt"))
        assertTrue(hash == HashingUtil.hash(correctPassword, "SHA-512", "salt"))
    }
}
