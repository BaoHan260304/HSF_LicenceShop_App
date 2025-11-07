package com.quannm.hsf_licenseshop_app.dao;

import com.quannm.hsf_licenseshop_app.entity.Warehouse;
import com.quannm.hsf_licenseshop_app.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class WarehouseDAO {

    public Optional<Warehouse> findByItemData(String itemData) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<Warehouse> query = em.createQuery(
                    "SELECT w FROM Warehouse w JOIN FETCH w.product WHERE w.itemData = :itemData",
                    Warehouse.class
            );
            query.setParameter("itemData", itemData);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Warehouse save(Warehouse warehouse) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            em.getTransaction().begin();
            Warehouse savedWarehouse = em.merge(warehouse);
            em.getTransaction().commit();
            return savedWarehouse;
        } catch (Exception e) {
            // In a real app, you'd want to handle this more gracefully
            e.printStackTrace();
            // Rollback transaction if it's active
            // if (em.getTransaction().isActive()) { em.getTransaction().rollback(); }
            throw e;
        }
    }
}