package org.application.services.impl;

import org.application.dao.ConnectionPool;
import org.application.dao.RoleDAO;
import org.application.dao.impl.RoleDAOImpl;
import org.application.models.Role;
import org.application.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public Role getRole(int id) {
        try (Connection con = ConnectionPool.getConnection()) {
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                return roleDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Role getRoleByName(String name) {
        try (Connection con = ConnectionPool.getConnection()) {
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                return roleDAO.getByName(name);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        try (Connection con = ConnectionPool.getConnection()) {
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                return roleDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Role createRole(Role role) {
        try (Connection con = ConnectionPool.getConnection()) {
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                con.setAutoCommit(false);
                roleDAO.create(role);
                con.commit();
                return role;
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
    public void updateRole(Role role) {
        try (Connection con = ConnectionPool.getConnection()) {
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                con.setAutoCommit(false);
                roleDAO.update(role);
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
    public void deleteRole(int id) {
        try (Connection con = ConnectionPool.getConnection()) {
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                con.setAutoCommit(false);
                roleDAO.delete(id);
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
}
