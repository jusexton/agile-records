package script.groovy.DatabasePopulator

/**
 * Currently contains random number generation tools.
 */
class MathUtil {
    static int getRandomInt(int min, int max) {
        return Math.abs(new Random().nextInt() % max + min)
    }

    static double getRandomDouble(int min, int max) {
        return min + (max - min) * new Random().nextDouble()
    }
}
