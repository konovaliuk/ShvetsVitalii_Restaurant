package org.application.dao.impl;

import org.application.dao.StatusDAO;
import org.application.models.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDAOImpl implements StatusDAO{
    private static final String SQL_SELECT_ALL_STATUSES = "SELECT * FROM statuses";
    private static final String SQL_SELECT_STATUS_BY_ID = "SELECT * FROM statuses WHERE status_id = ?";
    private static final String SQL_UPDATE_STATUS = "UPDATE statuses SET `name` = ? WHERE status_id = ?";
    private static final String SQL_ADD_STATUS = "INSERT INTO statuses (`name`) VALUES (?)";
    private static final String SQL_DELETE_STATUS = "DELETE FROM statuses WHERE status_id = ?";
    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(StatusDAOImpl.class);
    public StatusDAOImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Status get(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_STATUS_BY_ID)){
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getStatus(rs);
                }
            }
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    private Status getStatus(ResultSet rs) {
        try {
            int id = rs.getInt("status_id");
            String name = rs.getString("name");
            return new Status(id, name);
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Status> getAll() {
        try(PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_STATUSES)){
            List<Status> statuses = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Status status = getStatus(rs);
                    statuses.add(status);
                }
                return statuses;
            }
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Status create(Status status) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_STATUS, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, status.getName());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    status.setStatusId(rs.getInt(1));
                    return status;
                }
            }
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void update(Status status) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_STATUS)){
            stmt.setString(1, status.getName());
            stmt.setInt(2, status.getStatusId());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_STATUS)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }
}
