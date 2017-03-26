package script.groovy.DatabasePopulator

/**
 * Currently contains random number generation tools.
 */
class MathUtil {
    static int getRandomInt(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min
    }

    static double getRandomDouble(double min, double max) {
        return min + (max - min) * new Random().nextDouble()
    }
}
