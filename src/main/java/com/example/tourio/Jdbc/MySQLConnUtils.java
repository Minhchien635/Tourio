package com.example.tourio.Jdbc;

import java.sql.*;

public class MySQLConnUtils {

    public static Connection getMySQLConnection() throws SQLException {
        String hostName = "localhost";

        String dbName = "tourio";
        String userName = "root";

        String password = "06072000";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
}
