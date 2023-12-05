package dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;
import models.Quote;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MySQLQuotesDAO {
    // initialize the connection to null so we know whether or not to close it when done
    protected Connection connection = null;

    public void createConnection() throws MySQLAlbumsTest {
        System.out.print("Trying to connect... ");
        try {
            //TODO: create the connection and assign it to the instance variable
// register driver
            DriverManager.registerDriver(new Driver());

            // establish connection
            connection = DriverManager.getConnection(
                    Config.getUrl(),
                    Config.getUser(),
                    Config.getPassword()
            );
            System.out.println("connection created.");
        } catch (SQLException e) {
            throw new MySQLAlbumsTest("connection failed!!!");
        }
    }

    public void closeConnection() {
        if(connection == null) {
            System.out.println("Connection aborted.");
            return;
        }
        try {
            //TODO: close the connection
            connection.close();
            System.out.println("Connection closed.");
        } catch(SQLException e) {
            // ignore this
        }
    }

    public List<Quote> getQuotes() {
        return null;
    }
}
