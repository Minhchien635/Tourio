package com.tourio.dao;

import com.tourio.utils.HibernateUtils;
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

    public static Tour getDetails(long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            return session.find(Tour.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean createTour(Tour tour) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();



            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
