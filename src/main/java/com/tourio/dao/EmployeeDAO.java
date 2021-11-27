package com.tourio.dao;

import com.tourio.models.Employee;
import com.tourio.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class EmployeeDAO {
    public static List<Employee> getAll() {
        Session session = HibernateUtils.getSession();

        try {
            return HibernateUtils.getAllData(Employee.class, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();

        return null;
    }


    public static void save(Employee employee) {
        Session session = HibernateUtils.getSession();
        session.clear();
        session.beginTransaction();

        try {
            session.saveOrUpdate(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }

    public static void delete(Employee employee) {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        try {
            session.delete(employee);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }
}
