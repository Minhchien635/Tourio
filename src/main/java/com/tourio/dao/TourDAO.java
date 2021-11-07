package com.tourio.dao;

import com.tourio.models.Tour;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class TourDAO {

    public static List<Tour> getAll() {
        Session session = HibernateUtils.openSession();

        try {
            return HibernateUtils.getAllData(Tour.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }

    public static Tour getDetails(long id) {
        Session session = HibernateUtils.openSession();

        try {
            return session.find(Tour.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }
}
