package com.tourio.dao;

import com.tourio.models.Tour;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class TourDAO {

    public static List<Tour> getAll() {
        Session session = HibernateUtils.getSession();
        session.clear();

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

    public static void save(Tour tour) {
        Session session = HibernateUtils.getSession();
        session.clear();
        session.beginTransaction();

        System.out.println(tour.getTourLocationRels());

        try {
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