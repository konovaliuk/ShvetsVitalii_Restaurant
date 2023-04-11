package org.application.dao.impl;

import jakarta.persistence.EntityManager;
import org.application.dao.OrderDAO;
import org.application.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);
    public OrderDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public Order get(int id) {
        return em.find(Order.class, id);
    }

    @Override
    public List<Order> getAll() {
        return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

    @Override
    public Order create(Order order) {
        em.persist(order);
        return order;
    }

    @Override
    public void update(Order order) {
        em.merge(order);
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(Order.class, id));
    }
}
