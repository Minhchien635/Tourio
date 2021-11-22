package com.tourio.dao;

import com.tourio.models.Location;
import com.tourio.models.TourType;
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
}
