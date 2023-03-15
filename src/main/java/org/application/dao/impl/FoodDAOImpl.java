package org.application.dao.impl;

import org.application.dao.FoodDAO;
import org.application.models.Category;
import org.application.models.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FoodDAOImpl implements FoodDAO {
    private static final String SQL_SELECT_ALL_FOOD = "SELECT * FROM food JOIN categories_food USING (food_id) " +
            "JOIN categories USING (category_id)";
    private static final String SQL_SELECT_FOOD_BY_ID = "SELECT * FROM food JOIN categories_food USING (food_id) " +
            "JOIN categories USING (category_id) WHERE food_id = ?";
    private static final String SQL_SELECT_FOOD_BY_NAME = "SELECT * FROM food JOIN categories_food USING (food_id) " +
            "JOIN categories USING (category_id) WHERE food.name = ?";
    private static final String SQL_UPDATE_FOOD = "UPDATE food SET `name` = ?, unit_price = ?, " +
            "`description` = ?, image_path = ? WHERE food_id = ?";
    private static final String SQL_ADD_FOOD = "INSERT INTO food (`name`, unit_price, `description`, image_path)" +
            " VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE_FOOD = "DELETE FROM food WHERE food_id = ?";
    private static final Logger logger = LoggerFactory.getLogger(FoodDAOImpl.class);
    private final Connection con;

    public FoodDAOImpl(Connection con) {
        this.con = con;
    }
    @Override
    public Food get(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_FOOD_BY_ID)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getFood(rs);
                }
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Food> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_FOOD)) {
            try(ResultSet rs = stmt.executeQuery()) {
                List<Food> foods = new ArrayList<>();
                while (rs.next()) {
                    foods.add(getFood(rs));
                }
                return foods;
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Food getByName(String name) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_FOOD_BY_NAME)) {
            stmt.setString(1, name);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getFood(rs);
                }
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    private Food getFood(ResultSet rs) {
        try {
            List<Category> categories = new ArrayList<>();
            int id = rs.getInt("food_id");
            String name = rs.getString("name");
            double unitPrice = rs.getDouble("unit_price");
            String description = rs.getString("description");
            String imagePath = rs.getString("image_path");
            do{
                if(rs.getInt("food_id") != id) {
                    return new Food(id, name, unitPrice, description, imagePath, categories);
                }
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("categories.name");
                Category category = new Category(categoryId, categoryName);
                categories.add(category);
            } while(rs.next());
            return new Food(id, name, unitPrice, description, imagePath, categories);
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Food create(Food food) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_FOOD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, food.getName());
            stmt.setDouble(2, food.getUnitPrice());
            stmt.setString(3, food.getDescription());
            stmt.setString(4, food.getImagePath());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    food.setFoodId(rs.getInt(1));
                }
            }
            return food;
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void update(Food food) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_FOOD)) {
            stmt.setString(1, food.getName());
            stmt.setDouble(2, food.getUnitPrice());
            stmt.setString(3, food.getDescription());
            stmt.setString(4, food.getImagePath());
            stmt.setInt(5, food.getFoodId());
            stmt.executeUpdate();
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_FOOD)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }
}
