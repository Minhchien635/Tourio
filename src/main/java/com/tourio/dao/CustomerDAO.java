package com.tourio.dao;

import com.tourio.models.Customer;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class CustomerDAO {

    public static List<Customer> getAll() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(Customer.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }

    public static void save(Customer customer) {
        Session session = HibernateUtils.getSession();
        session.clear();
        session.beginTransaction();

        try {
            session.saveOrUpdate(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }

    public static void delete(Customer customer) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.delete(customer);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }
}
