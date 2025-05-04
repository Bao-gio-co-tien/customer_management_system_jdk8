package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://DESKTOP-RGG2M3U:1433;databaseName=CustomerDB;encrypt=false";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "HongChu2004";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
