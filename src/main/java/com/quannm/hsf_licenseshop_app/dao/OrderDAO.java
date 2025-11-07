package com.quannm.hsf_licenseshop_app.dao;

import com.quannm.hsf_licenseshop_app.entity.Order;
import com.quannm.hsf_licenseshop_app.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

public class OrderDAO {

    public List<Order> findByBuyerId(Long buyerId) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<Order> query = em.createQuery(
                    "SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.buyerId = :buyerId ORDER BY o.createdAt DESC", Order.class);
            query.setParameter("buyerId", buyerId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}