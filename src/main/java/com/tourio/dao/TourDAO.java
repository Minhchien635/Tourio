package com.tourio.dao;

import com.tourio.models.*;
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

    public static Tour getDetails(long id) {
        Session session = HibernateUtils.getSession();

        try {
            return session.find(Tour.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();

        return null;
    }

    public static List<TourType> getTypes() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(TourType.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }

    public static List<Location> getLocations() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(Location.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }

    public static List<TourPrice> getPrices() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(TourPrice.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }

    public static boolean createTour(Tour tour1, List<TourPrice> prices, List<TourLocationRel> locations) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.save(tour1);
            Tour tour = session.load(Tour.class, tour1.getId());

//            long i = 1;
//            for (Location location : locations) {
//                TourLocationRelID tourLocationRelId = new TourLocationRelID();
//                TourLocationRel tourLocationRel = new TourLocationRel();
//
//                tourLocationRelId.setTourId(tour.getId());
//                tourLocationRelId.setLocationId(location.getId());
//
//                tourLocationRel.setId(tourLocationRelId);
//                tourLocationRel.setLocation(location);
//                tourLocationRel.setTour(tour);
//                tourLocationRel.setSequence(i);
//
//                session.save(tourLocationRel);
//
//                i++;
//            }

            long i = 1;
            for (TourLocationRel tourLocationRel : locations) {
                Location location = tourLocationRel.getLocation();

                TourLocationRelID tourLocationRelId = new TourLocationRelID();
                tourLocationRelId.setTourId(tour.getId());
                tourLocationRelId.setLocationId(location.getId());

                tourLocationRel.setId(tourLocationRelId);
                tourLocationRel.setLocation(location);
                tourLocationRel.setTour(tour);
                tourLocationRel.setSequence(i);

                session.save(tourLocationRel);

                i++;
            }

            for (TourPrice price : prices) {
                // price.setTourId(tour.getId());
                price.setTour(tour);
                session.save(price);
            }

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
        return false;
    }

    public static boolean createTour(Tour tour) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.save(tour);
            session.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
        return false;
    }

    public static boolean updateTour(Tour tour) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.update(tour);
            session.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
        return false;
    }

    public static boolean saveTour(Tour tour) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(tour);
            session.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
        return false;
    }

    public static boolean updateTour(Tour tour1, List<TourPrice> prices, List<Location> locations) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.merge(tour1);
            Tour tour = session.load(Tour.class, tour1.getId());

            List<TourLocationRel> tourLocationRels = tour.getTourLocationRels();

            for (TourLocationRel tourLocationRel : tourLocationRels) {
                session.delete(tourLocationRel);
            }

            long i = 1;
            for (Location location : locations) {

                TourLocationRelID tourLocationRelId = new TourLocationRelID();
                TourLocationRel tourLocationRel = new TourLocationRel();

                tourLocationRelId.setTourId(tour.getId());
                tourLocationRelId.setLocationId(location.getId());

                tourLocationRel.setId(tourLocationRelId);
                tourLocationRel.setLocation(location);
                tourLocationRel.setTour(tour);
                tourLocationRel.setSequence(i);

                session.save(tourLocationRel);

                i++;
            }


            for (TourPrice price : prices) {
                price.setId(tour.getId());
                session.merge(price);
            }

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
        return false;
    }


    public static boolean deleteTour(long id) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            Tour tour = session.find(Tour.class, id);

            if (tour != null) {
                List<TourLocationRel> tourLocationRels = tour.getTourLocationRels();
                for (TourLocationRel tourLocationRel : tourLocationRels) {
                    TourLocationRel tourLocationRel1 = session.find(TourLocationRel.class, tourLocationRel.getId());
                    if (tourLocationRel1 != null) {
                        session.delete(tourLocationRel1);
                    }
                }

                List<TourPrice> tourPrices = tour.getTourPrices();
                for (TourPrice tourPrice : tourPrices) {
                    TourPrice tourPrice1 = session.find(TourPrice.class, tourPrice.getId());
                    if (tourPrice1 != null) {
                        session.delete(tourPrice1);
                    }
                }

                session.delete(tour);
            }

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return false;
    }
}
