package test.script

import script.DatabasePopulator

/**
 */
class DatabasePopulatorTests extends GroovyTestCase {
    // Passed
    void testGetRandomInt(){
        1000.times {
            int randomInt = DatabasePopulator.getRandomInt(0, 100)
            // println(randomInt)
            assertTrue(randomInt >= 0 && randomInt <= 100)
        }
    }

    // Passed
    void testGetRandomDouble(){
        1000.times {
            double randomDouble = DatabasePopulator.getRandomDouble(0, 100)
            // println(randomDouble)
            assertTrue(randomDouble >= 0 && randomDouble <= 100)
        }
    }
}
