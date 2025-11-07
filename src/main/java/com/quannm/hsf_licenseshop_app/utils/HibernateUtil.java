package com.quannm.hsf_licenseshop_app.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {

    private static final String PERSISTENCE_UNIT_NAME = "HSF_LicenseShopPU";
    private static EntityManagerFactory factory;

    // Khởi tạo EntityManagerFactory một lần duy nhất
    static {
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}