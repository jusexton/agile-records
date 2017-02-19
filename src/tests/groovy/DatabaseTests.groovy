package tests.groovy

import main.java.util.database.DatabaseUtil

import java.sql.Connection

/**
 * Contains all tests involving the database
 */
class DatabaseTests extends GroovyTestCase {
    // Test passes
    void testConnection() {
        final String host = "jdbc:mysql://gator4196.hostgator.com:3306/txscypaa_agilerecords"
        final String password = "txscypaa_agile"
        final String dbName = "4@lq^tsFiI0b"

        Connection connection = DatabaseUtil.getConnection(host, password, dbName)
        assertNotNull connection
    }

}
