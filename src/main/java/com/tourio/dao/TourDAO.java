package com.tourio.dao;

import com.tourio.dto.TourDTO;
import com.tourio.jdbc.HibernateUtils;
import com.tourio.models.Tour;
import com.tourio.models.TourLocationRel;
import com.tourio.models.TourPrice;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class TourDAO {

    public static List<Tour> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            return session.createQuery("from Tour").list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TourDTO getDetails(long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            Tour tour = session.find(Tour.class, id);

            ArrayList<TourPrice> tourPrices = new ArrayList<>(tour.getPrices());

            List<TourLocationRel> tourLocationRels = tour.getTourRels();

            return new Tour(tour.getId(), tour.getName(), tour.getType(), tourPrices, tour.getDescription(), tourLocationRels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
