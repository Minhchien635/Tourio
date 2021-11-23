package com.tourio.dao;

import com.tourio.models.Location;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class LocationDAO {
    public static List<Location> getAll() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(Location.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }


    public static void save(Location location) {
        Session session = HibernateUtils.getSession();
        session.clear();
        session.beginTransaction();

        try {
            session.saveOrUpdate(location);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }

    public static void delete(Location location) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.delete(location);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }
}
