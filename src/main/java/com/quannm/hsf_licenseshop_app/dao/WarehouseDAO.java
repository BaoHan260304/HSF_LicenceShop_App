package com.quannm.hsf_licenseshop_app.dao;

import com.quannm.hsf_licenseshop_app.entity.Warehouse;
import com.quannm.hsf_licenseshop_app.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
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

    public void saveAll(List<Warehouse> warehouses) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            for (int i = 0; i < warehouses.size(); i++) {
                em.persist(warehouses.get(i));
                // Cứ mỗi 50 bản ghi thì flush một lần để giải phóng bộ nhớ
                if (i > 0 && i % 50 == 0) {
                    em.flush();
                    em.clear();
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}