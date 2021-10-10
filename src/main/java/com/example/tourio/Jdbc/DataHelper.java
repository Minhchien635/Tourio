package com.example.tourio.Jdbc;

import java.sql.*;

public class DataHelper {
    public static Connection connection = null;

    public static ResultSet execQuery(String query) {
        try {
            if (connection == null) {
                connection = ConnectionUtils.getMyConnection();
            }
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static boolean execAction(String query) {
        try {
            if (connection == null) {
                connection = ConnectionUtils.getMyConnection();
            }
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
 