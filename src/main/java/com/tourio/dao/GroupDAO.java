package com.tourio.dao;

import com.tourio.models.Group;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class GroupDAO {

    public static List<Group> getAll() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(Group.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }

    public static void save(Group group) {
        Session session = HibernateUtils.getSession();
        session.clear();
        session.beginTransaction();

        try {
            session.saveOrUpdate(group);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }

    public static void delete(Group group) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.delete(group);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }
}