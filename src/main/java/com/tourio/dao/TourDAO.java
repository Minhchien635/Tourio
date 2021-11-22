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
        session.beginTransaction();

        try {
            for (TourPrice tourPrice : tourPrices) {
                tourPrice.setTour(tour);
            }

            tour.setTourPrices(tourPrices);

            long sequence = 1;
            for (TourLocationRel tourLocationRel : tourLocationRels) {
                tourLocationRel.setTour(tour);
                tourLocationRel.setSequence(sequence);
                tourLocationRel.setId(new TourLocationRelID());

                sequence++;
            }

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
        session.beginTransaction();

        try {
            for (TourPrice tourPrice : tourPrices) {
                if (tourPrice.getId() == null) {
                    tourPrice.setTour(tour);
                }
            }

            tour.getTourPrices().clear();
            tour.getTourPrices().addAll(tourPrices);

            long sequence = 1;
            for (TourLocationRel tourLocationRel : tourLocationRels) {
                if (tourLocationRel.getId() == null) {
                    tourLocationRel.setTour(tour);
                    tourLocationRel.setId(new TourLocationRelID());
                }
                else {
                    TourLocationRelID id = new TourLocationRelID();
                    id.setTourId(tour.getId());
                    id.setLocationId(tourLocationRel.getLocation().getId());
                    tourLocationRel.setId(id);
                }

                tourLocationRel.setSequence(sequence);

                sequence++;
            }

            tour.getTourLocationRels().clear();
            tour.getTourLocationRels().addAll(tourLocationRels);

            tour.getTourPrices().clear();
            tour.getTourPrices().addAll(tourPrices);

            session.saveOrUpdate(tour);

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
