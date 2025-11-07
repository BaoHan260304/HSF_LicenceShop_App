package com.quannm.hsf_licenseshop_app.dao;

import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

public class UserDAO {

    public Optional<User> findByUsername(String username) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            // Dùng getSingleResult() vì username là unique, nếu không tìm thấy sẽ ném NoResultException
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            // Trả về Optional rỗng nếu không tìm thấy user
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }

    public Optional<User> findById(Long id) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(User.class, id));
        }
    }

    public User update(User user) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            User updatedUser = em.merge(user);
            em.getTransaction().commit();
            return updatedUser;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw e; // Ném lại exception để tầng service có thể xử lý
        } finally {
            em.close();
        }
    }
}