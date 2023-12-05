package dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;

import java.sql.*;

public class MySQLAlbumsDAO {
    // initialize the connection to null so we know whether or not to close it when done
    private Connection connection = null;

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

    public int getTotalAlbums() throws MySQLAlbumsTest {
        int count = 0;
        try {
            //TODO: fetch the total number of albums from the albums table and assign it to the local variable


            Statement statement = connection.createStatement();

            // OPTION 1: LOOP OVER RESULTSET AND INCREMENT COUNT
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM codeup_test_db.albums");
//                while (resultSet.next()){
//                    count++;
//                }

            // OPTION 2: GET THE COUNT(*) AND DISPLAY IT
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM codeup_test_db.albums");
            resultSet.next();
            // resultSet get... methods accept either a column name
            // or a column index
            count = resultSet.getInt(1);


        } catch (SQLException e) {
            throw new MySQLAlbumsTest("Error executing query: " + e.getMessage() + "!!!");
        }
        return count;
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

}
