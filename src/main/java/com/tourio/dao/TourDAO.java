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
        String sql1 = "select tour.ID as tourId, tour.name, tourtype.name as type, description, tourprice.amount\n" +
                "from tourio.tourtype\n" +
                "\tinner join tourio.tour\n" +
                "\t\ton tourtype.ID=tour.type\n" +
                "\tinner join tourio.tourprice\n" +
                "\t\ton tourprice.tour=tour.ID\n" +
                "where tourio.tour.ID=" + tourId;
        String sql2 = "select tour.ID as tourId, tour.name, tourtype.name as type, description\n" +
                "from tourio.tourtype\n" +
                "\tinner join tourio.tour\n" +
                "\t\ton tourtype.ID=tour.type\n" +
                "\tinner join tourio.tourprice\n" +
                "\t\ton tourprice.tour!=tour.ID\n" +
                "where tourio.tour.ID=" + tourId;

        ResultSet rsTourPrice = DBUtils.executeQuery(sql1);
        ResultSet rsNotTourPrice = DBUtils.executeQuery(sql2);
        try {
            while (rsTourPrice.next()) {
                int Id = rsTourPrice.getInt("tourId");
                String name = rsTourPrice.getString("name");
                String type = rsTourPrice.getString("type");
                String description = rsTourPrice.getString("description");
                float price = rsTourPrice.getFloat("amount");
                tour = new TourDTO(Id, name, type, price, description);
            }

            while (rsNotTourPrice.next()) {
                int Id = rsNotTourPrice.getInt("tourId");
                String name = rsNotTourPrice.getString("name");
                String type = rsNotTourPrice.getString("type");
                String description = rsNotTourPrice.getString("description");
                float price = 0;
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
