package com.quannm.hsf_licenseshop_app.dao;

import com.quannm.hsf_licenseshop_app.entity.Product;
import com.quannm.hsf_licenseshop_app.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductDAO {

    public List<Product> findByShopId(Long shopId) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<Product> query = em.createQuery(
                    "SELECT p FROM Product p JOIN FETCH p.shop JOIN FETCH p.stall WHERE p.shop.id = :shopId ORDER BY p.name", Product.class);
            query.setParameter("shopId", shopId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Optional<Product> findById(long id) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(Product.class, id));
        }
    }

    public Product save(Product product) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Product savedProduct = em.merge(product);
            em.getTransaction().commit();
            return savedProduct;
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