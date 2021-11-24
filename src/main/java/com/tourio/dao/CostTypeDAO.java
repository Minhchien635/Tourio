package com.tourio.dao;

import com.tourio.models.CostType;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class CostTypeDAO {
    public static List<CostType> getAll() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(CostType.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }


    public static void save(CostType costType) {
        Session session = HibernateUtils.getSession();
        session.clear();
        session.beginTransaction();

        try {
            session.saveOrUpdate(costType);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }

    public static void delete(CostType costType) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.delete(costType);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }
}
