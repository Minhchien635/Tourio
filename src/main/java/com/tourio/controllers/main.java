package com.tourio.controllers;

import com.tourio.models.Location;
import com.tourio.models.Tour;
import com.tourio.models.TourLocationRel;
import com.tourio.models.TourLocationRelId;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

public class main {
    public static void main(String[] args) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            Tour tour = session.find(Tour.class, (long) 1);
            Location location = session.find(Location.class, (long) 3);

            TourLocationRel tourLocationRel = new TourLocationRel();
            TourLocationRelId tourLocationRelId = new TourLocationRelId();
            tourLocationRelId.setLocationId(3);
            tourLocationRelId.setTourId(1);

            tourLocationRel.setTour(tour);
            tourLocationRel.setLocation(location);

            tourLocationRel.setTourLocationRelId(tourLocationRelId);
            tourLocationRel.setSequence(5);
            session.save(tourLocationRel);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
