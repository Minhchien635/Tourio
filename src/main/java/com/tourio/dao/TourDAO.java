package com.tourio.dao;

import com.tourio.dto.TourDTO;
import com.tourio.jdbc.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TourDAO {

    public static ArrayList<TourDTO> getTours() {
        ArrayList<TourDTO> tours = new ArrayList<>();

        String sql = """
                SELECT name, id
                FROM tour
                """;

        ResultSet rs = DBUtils.executeQuery(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                tours.add(new TourDTO(id, name));
            }
            return tours;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tours;
    }

    public static TourDTO getTourDetail(int tourId) {
        String sql = """
                SELECT tour.id,
                       tour.name,
                       tour.description,
                       tour_type.name as type,
                       tour_price.amount
                FROM tour
                INNER JOIN tour_type
                        ON tour.type_id = tour_type.id
                INNER JOIN tour_price
                        ON tour.id = tour_price.tour_id
                WHERE tour.id = %d
                """
                .formatted(tourId);

        ResultSet rs = DBUtils.executeQuery(sql);
        try {
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String type = rs.getString("type");
                float price = rs.getFloat("amount");
                return new TourDTO(id, name, type, price, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getTourLocation(int tourId) {
        ArrayList<String> locations = new ArrayList<>();

        String sql = """
                SELECT location.name, 
                       tour_location_rel.sequence
                FROM tour
                INNER JOIN tour_location_rel
                        ON tour.id = tour_location_rel.tour_id
                INNER JOIN location
                        ON location.id = tour_location_rel.location_id
                WHERE tour.id = %d
                """
                .formatted(tourId);

        ResultSet rs = DBUtils.executeQuery(sql);
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int sequence = rs.getInt("sequence");
                locations.add(sequence + " - " + name);
            }
            locations.sort(String::compareToIgnoreCase);
            return locations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }
}
