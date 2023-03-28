package org.application.dao.impl;

import org.application.dao.CategoriesDAO;
import org.application.models.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDAOImpl implements CategoriesDAO {
    private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
    private static final String SQL_SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE category_id = ?";
    private static final String SQL_UPDATE_CATEGORY = "UPDATE categories SET `name` = ? WHERE category_id = ?";
    private static final String SQL_ADD_CATEGORY = "INSERT INTO categories (`name`) VALUES (?)";
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM categories WHERE category_id = ?";
    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(CategoriesDAOImpl.class);
    public CategoriesDAOImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Category get(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_CATEGORY_BY_ID)){
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getCategory(rs);
                }
            }
        }
        catch (SQLException ex) {
           logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    private Category getCategory(ResultSet rs) {
        try {
            int id = rs.getInt("category_id");
            String name = rs.getString("name");
            return new Category(id, name);
        }
        catch (SQLException ex) {
           logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Category> getAll() {
       try(PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_CATEGORIES)){
           List<Category> categories = new ArrayList<>();
           try (ResultSet rs = stmt.executeQuery()) {
               while (rs.next()) {
                   Category category = getCategory(rs);
                   categories.add(category);
               }
               return categories;
           }
       }
       catch (SQLException ex) {
          logger.error("Error: " + ex.getMessage());
       }
       return null;
    }

    @Override
    public Category create(Category category) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_CATEGORY, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, category.getName());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    category.setCategoryId(rs.getInt(1));
                    return category;
                }
            }
        }
        catch (SQLException ex) {
           logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void update(Category category) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_CATEGORY)){
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getCategoryId());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
           logger.error("Error: " + ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_CATEGORY)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
           logger.error("Error: " + ex.getMessage());
        }
    }
}
