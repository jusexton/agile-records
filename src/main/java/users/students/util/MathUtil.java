package main.java.users.students.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
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
}
