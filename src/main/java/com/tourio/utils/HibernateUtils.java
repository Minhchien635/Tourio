package com.tourio.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private HibernateUtils() {
        super();
    }

    private static SessionFactory buildSessionFactory() {
        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close() {
        getSessionFactory().close();
    }
}
