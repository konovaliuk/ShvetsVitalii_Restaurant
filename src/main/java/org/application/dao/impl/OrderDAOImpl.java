package org.application.dao.impl;

import org.application.dao.OrderDAO;
import org.application.models.Order;
import org.application.models.OrderFood;
import org.application.models.Status;
import org.application.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private static final String SQL_SELECT_ALL_ORDERS = "SELECT * FROM `order`";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT * FROM `order` WHERE order_id = ?";
    private static final String SQL_INSERT_ORDER = "INSERT INTO `order` (status_id, user_id, order_date, " +
            "total, waiter_id, chef_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_ORDER = "UPDATE `order` SET status_id = ?, user_id = ?, order_date = ?, " +
            "total = ?, waiter_id = ?, chef_id = ? " +
            "WHERE order_id = ?";
    private static final String DELETE = "DELETE FROM `order` WHERE order_id = ?";
    private static final Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);
    private final Connection con;

    public OrderDAOImpl(Connection con) {
        this.con = con;
    }
    @Override
    public Order get(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getOrder(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_ORDERS)) {
            try(ResultSet rs = stmt.executeQuery()) {
                List<Order> orders = new ArrayList<>();
                while (rs.next()) {
                    orders.add(getOrder(rs));
                }
                return orders;
            }
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    private Order getOrder(ResultSet rs) {
        try{
            List<OrderFood> orderFoods = new OrderFoodDAOImpl(con).getById(rs.getInt("order_id"));
            User user = new UserDAOImpl(con).get(rs.getInt("user_id"));
            User waiter = new UserDAOImpl(con).get(rs.getInt("waiter_id"));
            User chef = new UserDAOImpl(con).get(rs.getInt("chef_id"));
            Status status = new StatusDAOImpl(con).get(rs.getInt("status_id"));
            int id = rs.getInt("order_id");
            String date = rs.getString("order_date");
            double total = rs.getDouble("total");
            return new Order(id, status, user, date, total, waiter, chef, orderFoods);
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Order create(Order order) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_INSERT_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            createUpdate(order, stmt);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    order.setOrderId(rs.getInt(1));
                }
            }
            return order;
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void update(Order order) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_ORDER)) {
            createUpdate(order, stmt);
            stmt.setInt(7, order.getOrderId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    private void createUpdate(Order order, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, order.getStatus().getStatusId());
        stmt.setInt(2, order.getUser().getUserId());
        stmt.setString(3, order.getOrderDate());
        stmt.setDouble(4, order.getOrderTotal());
        stmt.setInt(5, order.getWaiter().getUserId());
        stmt.setInt(6, order.getChef().getUserId());
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = con.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }
}
