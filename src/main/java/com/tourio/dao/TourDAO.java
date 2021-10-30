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

    public static ArrayList<TourDTO> getTours() {
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

    public static TourDTO getTourDetail(long tourId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();

            Tour tour = session.find(Tour.class, tourId);

            TourPrice tourPrice = session.find(TourPrice.class, tour.getId());

            List<TourLocationRel> tourLocationRels = tour.getTourRels();

            ArrayList<Location> locations = new ArrayList<>();

            for (TourLocationRel tourLocationRel : tourLocationRels) {
                locations.add(tourLocationRel.getLocation());
            }

            return new TourDTO(tour.getId(), tour.getName(), tour.getTourType().getName(), tourPrice.getAmount(), tour.getDescription(), locations);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
