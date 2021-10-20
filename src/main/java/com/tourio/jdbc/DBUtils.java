package com.tourio.jdbc;

import java.sql.*;

public class DBUtils {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tourio";
    private static final String DB_USER = "tourio";
    private static final String DB_PASSWORD = "tourio";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static ResultSet executeQuery(String sql) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void executeUpdate(String sql) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test connection
    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            System.out.println(connection);
            System.out.println("Get connection success.");
        } catch (Exception e) {
            System.out.println("Get connection failed.");
            e.printStackTrace();
        }
    }
}
