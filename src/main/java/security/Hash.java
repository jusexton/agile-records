package main.java.security;

import main.java.security.util.HashingUtil;

import java.security.NoSuchAlgorithmException;

/**
 * Class that represents a hashed password.
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

    public boolean sameSalt(Hash hash){
        return this.salt.equals(hash.getSalt());
    }

    public boolean checkPassword(String password){
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
