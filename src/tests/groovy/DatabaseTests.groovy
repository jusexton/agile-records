package tests.groovy

import main.java.util.database.DatabaseUtil

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection

/**
 * Contains all tests involving the database
 */
class DatabaseTests extends GroovyTestCase {
    // Test passes
    public static Connection getConnection() {
        final String host = "jdbc:mysql://gator4196.hostgator.com:3306/txscypaa_agilerecords"
        final String password = "txscypaa_agile"
        final String dbName = "4@lq^tsFiI0b"

        Connection connection = DatabaseUtil.getConnection(host, password, dbName)
        assertNotNull(connection)

        return connection;
    }

    void addStudent(){


    }

   void testQuery()
    {

        Statement stmt=connection.createStatement();

        ResultSet rs=stmt.executeQuery("select * from users");

        while(rs.next())
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(6));
        connection.close();

    }


}
