package org.application.dao.impl;

import jakarta.persistence.EntityManager;
import org.application.dao.OrderFoodDAO;
import org.application.models.OrderFood;
import org.application.models.OrderFoodId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderFoodDAOImpl implements OrderFoodDAO {
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(OrderFoodDAOImpl.class);

    public OrderFoodDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public OrderFood get(OrderFoodId id) {
        return em.find(OrderFood.class, id);
    }
    @Override
    public List<OrderFood> getByOrderId(int orderId) {
        return em.createQuery("SELECT o FROM OrderFood o WHERE o.orderId = :orderId", OrderFood.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    @Override
    public List<OrderFood> getAll() {
        return em.createQuery("SELECT o FROM OrderFood o", OrderFood.class)
                .getResultList();
    }

    @Override
    public OrderFood create(OrderFood orderFood) {
        em.persist(orderFood);
        return orderFood;
    }

    @Override
    public void update(OrderFood orderFood) {
        em.merge(orderFood);
    }

    @Override
    public void delete(OrderFoodId id) {
        em.remove(em.find(OrderFood.class, id));
    }
}
