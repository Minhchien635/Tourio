package com.tourio.dao;

import com.tourio.models.Tour;
import com.tourio.models.TourLocationRel;
import com.tourio.models.TourLocationRelID;
import com.tourio.models.TourPrice;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class TourDAO {

    public static List<Tour> getAll() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(Tour.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }

    public static Tour get(long id) {
        Session session = HibernateUtils.getSession();

        try {
            return session.find(Tour.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }

    public static void create(Tour tour, List<TourPrice> tourPrices, List<TourLocationRel> tourLocationRels) {
        Session session = HibernateUtils.getSession();
        session.clear();
        session.beginTransaction();

        try {
            tour.setTourPrices(tourPrices);

            tour.setTourLocationRels(tourLocationRels);

            session.save(tour);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }

    public static void update(Tour tour, List<TourPrice> tourPrices, List<TourLocationRel> tourLocationRels) {
        Session session = HibernateUtils.getSession();
        session.clear();
        session.beginTransaction();

        try {
            tour.getTourPrices().clear();
            tour.getTourPrices().addAll(tourPrices);

            tour.getTourLocationRels().clear();
            tour.getTourLocationRels().addAll(tourLocationRels);

            session.update(tour);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }

    public static void delete(Tour tour) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.delete(tour);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }
}
