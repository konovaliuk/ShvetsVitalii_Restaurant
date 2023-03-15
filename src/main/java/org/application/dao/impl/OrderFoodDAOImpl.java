package org.application.dao.impl;

import org.application.dao.OrderFoodDAO;
import org.application.models.Food;
import org.application.models.OrderFood;
import org.application.models.OrderedFoodUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderFoodDAOImpl implements OrderFoodDAO {
    private static final String SQL_SELECT_ALL_ORDER_FOOD = "SELECT * FROM order_food";
    private static final String SQL_SELECT_ORDER_FOOD_BY_ORDER_ID = "SELECT * FROM order_food  WHERE order_id = ?";
    private static final String SQL_INSERT_ORDER_FOOD = "INSERT INTO order_food (order_id, food_id, quantity, unit_price) " +
            "VALUES (?, ?, ?, ?)";
   private static final String SQL_UPDATE_ORDER_FOOD = "UPDATE order_food SET order_id = ?, food_id = ?, quantity = ?, unit_price = ? " +
            "WHERE order_id = ?";
    private static final String SQL_DELETE_ORDER_FOOD = "DELETE FROM order_food WHERE order_id = ?";
    private static final Logger logger = LoggerFactory.getLogger(OrderFoodDAOImpl.class);
    private final Connection con;

    public OrderFoodDAOImpl(Connection con) {
        this.con = con;
    }

    @Override
    public OrderFood get(int id) {
        throw new UnsupportedOperationException();
    }
    @Override
    public List<OrderFood> getById(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ORDER_FOOD_BY_ORDER_ID)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                List<OrderFood> orderFoods = new ArrayList<>();
                while (rs.next()) {
                    orderFoods.add(getOrderFood(rs));
                }
                return orderFoods;
            }
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<OrderFood> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_ORDER_FOOD);
             ResultSet rs = stmt.executeQuery()) {
            List<OrderFood> orderFoods = new ArrayList<>();
            while (rs.next()) {
                orderFoods.add(getOrderFood(rs));
            }
            return orderFoods;
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    private OrderFood getOrderFood(ResultSet rs) {
        try {
            List<OrderedFoodUnit> orderedFoods = new ArrayList<>();
            int orderId = rs.getInt("order_id");
            do{
                if (orderId != rs.getInt("order_id")) {
                    return new OrderFood(orderId, orderedFoods);
                }
                int foodId = rs.getInt("food_id");
                int quantity = rs.getInt("quantity");
                double unitPrice = rs.getDouble("unit_price");
                Food food = new FoodDAOImpl(con).get(foodId);
                orderedFoods.add(new OrderedFoodUnit(food, quantity, unitPrice));
            } while (rs.next());

            return new OrderFood(orderId, orderedFoods);
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public OrderFood create(OrderFood orderFood) {
        try(PreparedStatement stmt = con.prepareStatement(SQL_INSERT_ORDER_FOOD)) {
            for (OrderedFoodUnit orderedFoodUnit : orderFood.getOrderedFoodUnits()) {
                createUpdate(stmt, orderFood, orderedFoodUnit);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return orderFood;
    }

    @Override
    public void update(OrderFood orderFood) {
        try(PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_ORDER_FOOD)) {
            for (OrderedFoodUnit orderedFoodUnit : orderFood.getOrderedFoodUnits()) {
                createUpdate(stmt, orderFood, orderedFoodUnit);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    private void createUpdate(PreparedStatement stmt,  OrderFood orderFood, OrderedFoodUnit orderedFoodUnit) throws SQLException {
        stmt.setInt(1, orderFood.getOrderId());
        stmt.setInt(2, orderedFoodUnit.getFood().getFoodId());
        stmt.setInt(3, orderedFoodUnit.getQuantity());
        stmt.setDouble(4, orderedFoodUnit.getUnitPrice());
        stmt.executeUpdate();
     }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_ORDER_FOOD)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }
}
