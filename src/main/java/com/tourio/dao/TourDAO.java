package com.tourio.dao;

import com.tourio.dto.TourDTO;
import com.tourio.jdbc.HibernateUtils;
import com.tourio.models.Location;
import com.tourio.models.Tour;
import com.tourio.models.TourLocationRel;
import com.tourio.models.TourPrice;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class TourDAO {

    public static ArrayList<TourDTO> getAll() {
        ArrayList<TourDTO> tours = new ArrayList<>();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            List<Tour> tourList = session.createQuery("from Tour").list();
            for (Tour tour : tourList) {
                tours.add(new TourDTO(tour.getId(), tour.getName()));
            }

            return tours;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tours;
    }

    public static TourDTO getDetails(long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();

            Tour tour = session.find(Tour.class, id);

            ArrayList<TourPrice> tourPrices = new ArrayList<>(tour.getTourPrices());

            List<TourLocationRel> tourLocationRels = tour.getTourRels();

            ArrayList<Location> locations = new ArrayList<>();
            for (TourLocationRel tourLocationRel : tourLocationRels) {
                locations.add(tourLocationRel.getLocation());
            }

            TourDTO tourDTO = new TourDTO(tour.getId(), tour.getName(), tour.getTourType().getName(), tourPrices, tour.getDescription(), locations);

            return tourDTO;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
