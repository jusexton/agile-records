package main.java.util.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Contains tools for hashing operations.
 */
public abstract class HashingUtil {
    /**
     * Overloaded hash function. Uses no salt.
     *
     * @param content   The content that will be hashed.
     * @param algorithm The algorithm that will be used to hash the content.
     * @return The hashed content.
     * @throws NoSuchAlgorithmException Exception
     */

    public static Hash hash(final String content, final String algorithm)
            throws NoSuchAlgorithmException {
        return hash(content, algorithm, "");
    }

    /**
     * Hashes content and returns the result.
     *
     * @param content   The content that will be hashed.
     * @param algorithm The algorithm that will be used to hash the content.
     * @param salt      The salt that will be applied with the content when hashing.
     * @return The hashed content.
     * @throws NoSuchAlgorithmException Exception
     */
    public static Hash hash(final String content, final String algorithm, final String salt)
            throws NoSuchAlgorithmException {
        Hash hash = null;

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(content.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            hash = new Hash(sb.toString(), salt, algorithm);
        } catch (UnsupportedEncodingException ignored) {
            // This exception will never be caught.
        }
        return hash;
    }

    /**
     * Overloaded generateSalt function. Sets salt length to 20 and uses all characters
     * when generating the salt.
     *
     * @param random A random object.
     * @return Randomly generated string of all characters of length 20.
     */
    public static String generateSalt(final Random random) {
        return generateSalt(20, random);
    }

    /**
     * Overloaded generateSalt function. Sets charset to all characters.
     *
     * @param length The length of the generated string.
     * @param random A random object.
     * @return Randomly generated string.
     */
    public static String generateSalt(final int length, final Random random) {
        return generateSalt(length,
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy0123456789!@#$%^&*()_+=-[]{};':,.<>/?`~|\\ ",
                random);
    }

    /**
     * Randomly generates a string and returns the value.
     *
     * @param length  The length of the generated string.
     * @param charset The charset that will be used when generating the string.
     * @param random  A random object.
     * @return Randomly generated string.
     */
    public static String generateSalt(final int length, final String charset, final Random random) {
        return random.ints(length, 0, charset.length())
                .boxed()
                .map(i -> charset.substring(i, i + 1))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
