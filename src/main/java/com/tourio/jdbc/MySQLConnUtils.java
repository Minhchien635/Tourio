package com.tourio.jdbc;

import java.sql.*;

public class MySQLConnUtils {
    private static final String hostName = "localhost";
    private static final String dbName = "tourio";
    private static final String user = "tourio";
    private static final String password = "tourio";

    public static Connection getMySQLConnection() throws SQLException {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        return DriverManager.getConnection(connectionURL, user, password);
    }
}
