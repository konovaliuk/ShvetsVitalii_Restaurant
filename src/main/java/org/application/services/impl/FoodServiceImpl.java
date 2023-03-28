package org.application.services.impl;

import org.application.dao.ConnectionPool;
import org.application.dao.FoodDAO;
import org.application.dao.impl.FoodDAOImpl;
import org.application.models.Food;
import org.application.services.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class FoodServiceImpl implements FoodService {
    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);
    @Override
    public Food createFood(Food food) {
        try (Connection con = ConnectionPool.getConnection()) {
            FoodDAO foodDAO = new FoodDAOImpl(con);
            try {
                con.setAutoCommit(false);
                foodDAO.create(food);
                con.commit();
                return food;
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
    public void updateFood(Food food) {
        try (Connection con = ConnectionPool.getConnection()) {
            FoodDAO foodDAO = new FoodDAOImpl(con);
            try {
                con.setAutoCommit(false);
                foodDAO.update(food);
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
    public void deleteFood(int id) {
        try (Connection con = ConnectionPool.getConnection()) {
            FoodDAO foodDAO = new FoodDAOImpl(con);
            try {
                con.setAutoCommit(false);
                foodDAO.delete(id);
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
    public Food getFood(int id) {
        try (Connection con = ConnectionPool.getConnection()) {
            FoodDAO foodDAO = new FoodDAOImpl(con);
            try {
                return foodDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    @Override
    public List<Food> getAllFood() {
        try (Connection con = ConnectionPool.getConnection()) {
            FoodDAO foodDAO = new FoodDAOImpl(con);
            try {
                return foodDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
}
