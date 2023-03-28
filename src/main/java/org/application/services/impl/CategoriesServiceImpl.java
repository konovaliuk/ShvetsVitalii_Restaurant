package org.application.services.impl;


import org.application.dao.CategoriesDAO;
import org.application.dao.ConnectionPool;
import org.application.dao.impl.CategoriesDAOImpl;
import org.application.models.Category;
import org.application.services.CategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class CategoriesServiceImpl implements CategoriesService {
    private static final Logger logger = LoggerFactory.getLogger(CategoriesServiceImpl.class);
    @Override
    public Category createCategory(Category category) {
        try (Connection con = ConnectionPool.getConnection()) {
            CategoriesDAO categoriesDAO = new CategoriesDAOImpl(con);
            try {
                con.setAutoCommit(false);
                categoriesDAO.create(category);
                con.commit();
                return category;
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
    public void updateCategory(Category category) {
        try (Connection con = ConnectionPool.getConnection()) {
            CategoriesDAO categoriesDAO = new CategoriesDAOImpl(con);
            try {
                con.setAutoCommit(false);
                categoriesDAO.update(category);
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
    public void deleteCategory(int id) {
        try (Connection con = ConnectionPool.getConnection()) {
            CategoriesDAO categoriesDAO = new CategoriesDAOImpl(con);
            try {
                con.setAutoCommit(false);
                categoriesDAO.delete(id);
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
    public Category getCategory(int id) {
        try (Connection con = ConnectionPool.getConnection()) {
            CategoriesDAO categoriesDAO = new CategoriesDAOImpl(con);
            try {
                return categoriesDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Category> getAllCategory() {
        try (Connection con = ConnectionPool.getConnection()) {
            CategoriesDAO categoriesDAO = new CategoriesDAOImpl(con);
            try {
                return categoriesDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
}
