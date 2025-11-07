package com.quannm.hsf_licenseshop_app.dao;

import com.quannm.hsf_licenseshop_app.entity.Shop;
import com.quannm.hsf_licenseshop_app.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class ShopDAO {
    public Optional<Shop> findByUserId(Long userId) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<Shop> query = em.createQuery(
                    "SELECT s FROM Shop s WHERE s.userId = :userId", Shop.class);
            query.setParameter("userId", userId);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Shop save(Shop shop) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Shop savedShop = em.merge(shop);
            em.getTransaction().commit();
            return savedShop;
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