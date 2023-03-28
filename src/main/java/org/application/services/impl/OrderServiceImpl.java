package org.application.services.impl;

import org.application.dao.ConnectionPool;
import org.application.dao.OrderDAO;
import org.application.dao.impl.OrderDAOImpl;
import org.application.models.Order;
import org.application.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Order createOrder(Order order) {
        try (Connection con = ConnectionPool.getConnection()) {
            OrderDAO orderDAO = new OrderDAOImpl(con);
            try {
                con.setAutoCommit(false);
                orderDAO.create(order);
                con.commit();
                return order;
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e) {
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void updateOrder(Order order) {
        try (Connection con = ConnectionPool.getConnection()) {
            OrderDAO orderDAO = new OrderDAOImpl(con);
            try {
                con.setAutoCommit(false);
                orderDAO.update(order);
                con.commit();
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e) {
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    @Override
    public void deleteOrder(int id) {
        try (Connection con = ConnectionPool.getConnection()) {
            OrderDAO orderDAO = new OrderDAOImpl(con);
            try {
                con.setAutoCommit(false);
                orderDAO.delete(id);
                con.commit();
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e) {
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    @Override
    public Order getOrder(int id) {
        try (Connection con = ConnectionPool.getConnection()) {
            OrderDAO orderDAO = new OrderDAOImpl(con);
            try {
                return orderDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        try (Connection con = ConnectionPool.getConnection()) {
            OrderDAO orderDAO = new OrderDAOImpl(con);
            try {
                return orderDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
}
