package com.tourio.dao;

import com.tourio.models.Job;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class JobDAO {
    public static List<Job> getAll() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(Job.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }


    public static void save(Job job) {
        Session session = HibernateUtils.getSession();
        session.clear();
        session.beginTransaction();

        try {
            session.saveOrUpdate(job);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }

    public static void delete(Job job) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.delete(job);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }
}
