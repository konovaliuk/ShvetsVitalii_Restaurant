package org.application.services.impl;

import org.application.dao.ConnectionPool;
import org.application.dao.UserDAO;
import org.application.dao.impl.UserDAOImpl;
import org.application.models.User;
import org.application.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public List<User> getAllUsers() {
        try (Connection con = ConnectionPool.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                return userDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    @Override
    public List<User> getUsersByRole(User user) {
        try (Connection con = ConnectionPool.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                return userDAO.getByRole(user);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    @Override
    public User getUser(int id) {
        try (Connection con = ConnectionPool.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                return userDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    @Override
    public User registerUser(User user) {
        try (Connection con = ConnectionPool.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                con.setAutoCommit(false);
                user.setPassword(getHash(user.getPassword()));
                if (userDAO.create(user) != null) {
                    con.commit();
                    return user;
                }
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
    public User loginUser(String login, String password) {
        try (Connection con = ConnectionPool.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                User user = userDAO.getByLogin(login);
                if (checkPassword(password, user.getPassword())) {
                    return user;
                }
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    @Override
    public void updateUser(User user) {
        try (Connection con = ConnectionPool.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                con.setAutoCommit(false);
                user.setPassword(getHash(user.getPassword()));
                userDAO.update(user);
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
    public boolean deleteUser(int id, String password) {
        try (Connection con = ConnectionPool.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                con.setAutoCommit(false);
                User user = userDAO.get(id);
                if (checkPassword(user.getPassword(), getHash(password))) {
                    userDAO.delete(id);
                    con.commit();
                    return true;
                }
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
        return false;
    }
    private String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
