package com.tourio.dao;

import com.tourio.models.TourType;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class TourTypeDAO {
    public static List<TourType> getAll() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(TourType.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }
}
