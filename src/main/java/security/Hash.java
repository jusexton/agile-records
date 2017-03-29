package security;

import security.util.HashingUtil;

import java.security.NoSuchAlgorithmException;

/**
 * Entity class that represents a hashed password.
 */
public class Hash {
    private String hash;
    private String salt;
    private String algorithm;

    public Hash(String hash, String salt, String algorithm) {
        this.hash = hash;
        this.salt = salt;
        this.algorithm = algorithm;
    }

    /**
     * Returns whether a given salt is the same as the instanced salt.
     *
     * @param hash The hash object that will be checked.
     * @return Whether the salts match or not.
     */
    public boolean sameSalt(Hash hash) {
        return this.salt.equals(hash.getSalt());
    }

    /**
     * Checks whether the given password is valid.
     *
     * @param password The password to be checked.
     * @return Whether the given password was valid or not.
     */
    public boolean checkPassword(String password) {
        boolean valid = false;
        try {
            valid = this.equals(HashingUtil.hash(password, algorithm, salt));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return valid;
    }

    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object instanceof Hash) {
            Hash hash = (Hash) object;
            result = this.toString().equals(hash.toString());
        }
        return result;
    }

    @Override
    public String toString() {
        return hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
