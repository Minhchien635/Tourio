package com.tourio.dao;

import com.tourio.dto.TourDTO;
import com.tourio.jdbc.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TourDAO {

    public static ArrayList<TourDTO> getTours() {
        ArrayList<TourDTO> tours = new ArrayList<>();
        String sql = "select tour.name, tour.ID \n" +
                "from tourio.tour";
        ResultSet rs = DBUtils.executeQuery(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("name");

                tours.add(new TourDTO(id, name));
            }
            return tours;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tours;
    }

    public static TourDTO getTourDetail(int tourId) {
        TourDTO tour = null;
        String sql = "select tour.ID as tourId, tour.name, tourtype.name as type, description, tourprice.amount\n" +
                "from tourio.tourtype\n" +
                "\tinner join tourio.tour\n" +
                "\t\ton tourtype.ID=tour.type\n" +
                "\tinner join tourio.tourprice\n" +
                "\t\ton tourprice.tour=tour.ID\n" +
                "where tourio.tour.ID=" + tourId;

        ResultSet rs = DBUtils.executeQuery(sql);
        try {
            while (rs.next()) {
                int Id = rs.getInt("tourId");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String description = rs.getString("description");
                float price = rs.getFloat("amount");
                tour = new TourDTO(Id, name, type, price, description);
            }
            return tour;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tour;
    }

    public static ArrayList<String> getTourLocation(int tourId) {
        ArrayList<String> locations = new ArrayList<>();
        String sql = "select tourio.location.name, tourio.tourlocationrel.sequence\n" +
                "from tourio.tour tour\n" +
                "\tinner join tourio.tourlocationrel tourlocationrel\n" +
                "    on tourlocationrel.tour=tour.ID\n" +
                "    inner join tourio.location location\n" +
                "    on tourlocationrel.location=location.ID\n" +
                "where tour.ID=" + tourId;

        ResultSet rs = DBUtils.executeQuery(sql);
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int sequence = rs.getInt("sequence");
                locations.add(sequence + "-" + name);
            }
            locations.sort(String::compareToIgnoreCase);
            return locations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }
}
