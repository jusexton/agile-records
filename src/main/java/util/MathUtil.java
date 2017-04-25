package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Class containing math related tools.
 */
public abstract class MathUtil {
    /**
     * Returns a random integer within the given range.
     *
     * @param min The minimum number that can be returned.
     * @param max The maximum number that can be returned.
     * @return The random integer.
     */
    public static int getRandomInt(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min;
    }

    /**
     * Returns a random double within the given range.
     *
     * @param min The minimum number that can be returned.
     * @param max The maximum number that can be returned.
     * @return The random double.
     */
    public static double getRandomDouble(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

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
     * Checks whether a given string can be converted to an Integer or not.
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
     * Checks whether a given string can be converted to an Double or not.
     *
     * @param value The string value that will be checked.
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
     * @param value The string value that will be checked.
     * @return Whether the value is numeric or not.
     */
    public static boolean isNumeric(String value) {
        return value.matches("[-+]?\\d*\\.?\\d+");
    }

    /**
     * Determines whether given value is negative.
     *
     * @param numericValue The value that will be checked.
     * @return Whether the given value is negative or not.
     */
    public static boolean isNegative(String numericValue){
        return isNumeric(numericValue) && Double.parseDouble(numericValue) < 0.0;
    }
}
