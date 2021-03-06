package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.UserDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User add(User user) throws DataProcessingException {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long userId = (Long) session.save(user);
            transaction.commit();
            user.setId(userId);
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't create user", e);
        }
    }

    @Override
    public User findByEmail(String email) throws DataProcessingException {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email = :email");
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find by email");
        }
    }

    @Override
    public User findById(Long id) throws DataProcessingException {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't find by email");
        }
    }
}
