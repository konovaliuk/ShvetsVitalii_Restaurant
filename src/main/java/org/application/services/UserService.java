package org.application.services;

import org.application.models.User;

import java.util.List;

public interface UserService {
    User getUser(int id);
    List<User> getAllUsers();
    List<User> getUsersByRole(User user);
    User loginUser(String login, String password);
    User registerUser(User user);
    void updateUser(User user);
    boolean deleteUser(int id, String password);
}
