package com.tourio.dao;

import com.tourio.jdbc.DBUtils;
import com.tourio.models.Tour;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TourDAO {

    public static ArrayList<Tour> getTours(int n, int page, int pageSize) {
        try {
            String sql = "";
            ResultSet rs = DBUtils.executeQuery(sql);
            ArrayList<Tour> tours = new ArrayList<>();
            while (rs.next()) {
//                String name = rs.getString("name");
//                String description = rs.getString("description");
//                String type_name = rs.getString("type_name");
//                Tour tour = new Tour(name, description, type_name);
//                tours.add(tour);
            }
            return tours;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
