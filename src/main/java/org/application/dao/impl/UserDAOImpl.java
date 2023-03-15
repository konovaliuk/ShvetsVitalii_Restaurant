package org.application.dao.impl;

import org.application.dao.UserDAO;
import org.application.models.Role;
import org.application.models.User;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_USER_BY_ROLE = "SELECT * FROM users JOIN roles " +
            "USING (role_id) WHERE `name` = ?";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SQL_UPDATE_USER = "UPDATE users SET login = ?, `password` = ?, " +
            "email = ?, first_name = ?, last_name = ?, salary = ?, role_id = ? WHERE user_id = ?";
    private static final String SQL_ADD_USER = "INSERT INTO users (login, `password`, email, first_name, " +
            "last_name, salary, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    public UserDAOImpl(Connection con){
        this.con = con;
    }
    @Override
    public User get(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_USER_BY_ID)){
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    return getUser(rs);
                }
            }
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_USERS)){
            List<User> users = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = getUser(rs);
                    users.add(user);
                }
                return users;
            }
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    private User getUser(ResultSet rs){
        try {
            int id = rs.getInt("user_id");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            double salary = rs.getDouble("salary");
            Role role = new RoleDAOImpl(con).get(rs.getInt("role_id"));
            return new User(id, login, password, email, firstName, lastName, salary, role);
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getByRole(User user) {
        try (PreparedStatement stmt = con.prepareStatement( SQL_SELECT_USER_BY_ROLE)){
            stmt.setString(1, user.getRole().getName());
            try (ResultSet rs = stmt.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    User userGet = getUser(rs);
                    users.add(userGet);
                }
                return users;
            }
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public User getByLogin(String login) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_USER_BY_LOGIN)){
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getUser(rs);
                }
            }
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public User create(User user) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_USER, PreparedStatement.RETURN_GENERATED_KEYS)){
            createUpdate(stmt, user);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return user;
    }

    @Override
    public void update(User user) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_USER)){
            createUpdate(stmt, user);
            stmt.setInt(8, user.getUserId());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    private void createUpdate(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getLogin());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getFirstName());
        stmt.setString(5, user.getLastName());
        stmt.setDouble(6, user.getSalary());
        stmt.setInt(7, user.getRole().getRoleId());
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_USER)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    public String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
