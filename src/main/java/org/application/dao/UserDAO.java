package org.application.dao;

import org.application.models.User;

import java.util.List;

public interface UserDAO extends DAO<User>{
    List<User> getByRole(User user);
    User getByLogin(String login);
}
