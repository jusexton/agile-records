package test.groovy

import main.java.util.security.HashingUtil

import java.util.stream.Stream

/**
 * Contains all hashing tests
 */
class HashingTests extends GroovyTestCase {
    final static length = 20
    final static charset = "abc"
    final static random = new Random()
    final static salt = HashingUtil.generateSalt(length, charset, random)
    final static String password = "test_password"

    void testSaltLength() {
        assertLength(20, salt.toCharArray())
    }

    void testSaltCharset() {
        assertTrue salt.matches("^[" + charset + "_]+")
    }

    void testSaltRandomness() {
        final limiter = 1000
        // Generates 1000 salts, none of which should be filtered
        // out with the distinct() call.
        def saltCount = Stream.generate { HashingUtil.generateSalt(length, charset, random) }
                .limit(limiter)
                .distinct()
                .count()
        // salts.forEach {it -> println(it)}
        assertEquals(limiter, saltCount)
    }

    void testAlgorithms() {
        def hash = HashingUtil.hash(password, "SHA-256")
        assertEquals(hash, HashingUtil.hash(password, "SHA-256"))

        hash = HashingUtil.hash(password, "SHA-512")
        assertEquals(hash, HashingUtil.hash(password, "SHA-512"))
    }

    void testAlgorithmResults() {
        def hash = HashingUtil.hash(password, "SHA-256")
        shouldFail { assertEquals(hash, HashingUtil.hash("different_password", "SHA-512")) }
    }
}
