package com.quannm.hsf_licenseshop_app.dao;

import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

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
}