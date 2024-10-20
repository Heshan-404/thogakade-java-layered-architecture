package edu.iuhs.crm.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static Connection connection;
    private static DB instance;

    private DB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost/thogakade", "root", "1234");
    }

    public static DB getInstance() throws SQLException {
        return instance != null ? instance : new DB();
    }

    public static Connection getConnection() {
        return connection;
    }

}
