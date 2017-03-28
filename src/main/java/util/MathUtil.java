package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 */
public abstract class MathUtil {
    public static int getRandomInt(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min;
    }

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

    public static boolean isInteger(String value) {
        try {
            int i = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String value) {
        try {
            double d = Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String value) {
        return value.matches("[-+]?\\d*\\.?\\d+");
    }
}
