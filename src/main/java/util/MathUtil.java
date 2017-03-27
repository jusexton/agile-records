package main.java.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Contains tools for checking data types, rounding floating points
 * and building random numbers.
 */
public abstract class MathUtil {
    /**
     * Rounds a double value to the wanted place.
     *
     * @param value  The double value
     * @param places The nth place.
     * @return The round value.
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        return new BigDecimal(value)
                .setScale(places, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Checks whether a string value can become an integer.
     *
     * @param value The string value that will be checked
     * @return Whether or not the value can be converted or not.
     */
    public static boolean isInteger(String value) {
        try {
            int i = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether a string value can become a double.
     *
     * @param value The string value that will be checked
     * @return Whether or not the value can be converted or not.
     */
    public static boolean isDouble(String value) {
        try {
            double d = Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether a string value is numeric or not.
     *
     * @param value The string value that will be checked
     * @return Whether or not the value is numeric or not.
     */
    public static boolean isNumeric(String value) {
        return value.matches("[-+]?\\d*\\.?\\d+");
    }

    /**
     * Returns a random integer in the given range.
     *
     * @param min The smallest integer that will be returned.
     * @param max The largest integer that will be returned.
     * @return The random integer.
     */
    public static int getRandomInt(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min;
    }

    /**
     * Returns a random double in the given range.
     *
     * @param min The smallest double that will be returned.
     * @param max The largest double that will be returned.
     * @return The random double.
     */
    public static double getRandomDouble(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }
}
