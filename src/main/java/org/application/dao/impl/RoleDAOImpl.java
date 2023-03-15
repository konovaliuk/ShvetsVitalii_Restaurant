package org.application.dao.impl;

import org.application.dao.RoleDAO;
import org.application.models.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {
    private static final String SQL_SELECT_ALL_ROLES = "SELECT * FROM roles";
    private static final String SQL_SELECT_ROLE_BY_ID = "SELECT * FROM roles WHERE role_id = ?";
    private static final String SQL_ADD_ROLE = "INSERT INTO roles (`name`) VALUES (?)";
    private static final String SQL_UPDATE_ROLE = "UPDATE roles SET `name` = ? WHERE role_id = ?";
    private static final String SQL_DELETE_ROLE = "DELETE FROM roles WHERE role_id = ?";
    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);
    public RoleDAOImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Role get(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ROLE_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getRole(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    private Role getRole(ResultSet rs) {
        try {
            int id = rs.getInt("role_id");
            String name = rs.getString("name");
            return new Role(id, name);
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Role> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_ROLES)) {
            List<Role> roles = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Role role = getRole(rs);
                    roles.add(role);
                }
                return roles;
            }
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Role create(Role role) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_ROLE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, role.getName());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    role.setRoleId(rs.getInt(1));
                    return role;
                }
            }
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void update(Role role) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_ROLE)) {
            stmt.setString(1, role.getName());
            stmt.setInt(2, role.getRoleId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_ROLE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }
}
