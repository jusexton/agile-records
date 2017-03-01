package main.java.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 */
public class MathUtil {
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
}
