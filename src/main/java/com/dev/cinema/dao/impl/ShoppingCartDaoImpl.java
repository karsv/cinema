package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    private final SessionFactory sessionFactory;

    public ShoppingCartDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long shoppingCartId = (Long) session.save(shoppingCart);
            transaction.commit();
            shoppingCart.setId(shoppingCartId);
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add shopping cart", e);
        }
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            ShoppingCart shoppingCart = (ShoppingCart) session
                    .createQuery("From shopping_cart Where user = :user")
                    .setParameter("user", user)
                    .uniqueResult();
            List<Ticket> tickets = session.createQuery(
                    "FROM Ticket WHERE user = :user", Ticket.class)
                    .setParameter("user", user)
                    .getResultList();
            shoppingCart.setTickets(tickets);
            return shoppingCart;
        } catch (Exception e) {
            throw new DataProcessingException("Can't get shopping cart by user", e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(shoppingCart);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't update by user", e);
        }
    }

    @Override
    public ShoppingCart getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(ShoppingCart.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't find by id");
        }
    }
}
