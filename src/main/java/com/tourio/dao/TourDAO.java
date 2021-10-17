package com.tourio.dao;

import com.tourio.jdbc.DataHelper;
import com.tourio.models.Tour;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TourDAO {

    public static ArrayList<Tour> getTours() {
        ArrayList<Tour> tours = new ArrayList<>();
        String sql = "SELECT * FROM tour";
        ResultSet rs = DataHelper.execQuery(sql);
        try {
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                float price = rs.getFloat("price");
                String typeID = rs.getString("typeID");
                tours.add(new Tour(id, name, description, price, typeID));
            }
            return tours;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tours;
    }
}
