package com.tourio.dao;

import com.tourio.jdbc.HibernateUtils;
import com.tourio.dto.Tour;
import com.tourio.dto.TourLocationRel;
import com.tourio.dto.TourPrice;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class TourDAO {

    public static List<Tour> getTours() {
        List<Tour> tours = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();

            tours = session.createQuery("from Tour").list();

            return tours;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tours;
    }

    public static Tour getTourDetail(long tourId) {
        Tour tour = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();

            tour = session.find(Tour.class, tourId);

            ArrayList<TourPrice> tourPrices = new ArrayList<>(tour.getPrices());

            List<TourLocationRel> tourLocationRels = tour.getTourRels();

            tour = new Tour(tour.getId(), tour.getName(), tour.getType(), tourPrices, tour.getDescription(), tourLocationRels);

            return tour;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tour;
    }
}
