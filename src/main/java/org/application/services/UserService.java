package org.application.services;

import org.application.models.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User getUser(int id);
    List<User> getAllUsers();
    Page<User> getAllUsers(int page, int pageSize);
    List<User> getUsersByRole(String roleName);
    User loginUser(String login, String password);
    User registerUser(User user);
    void updateUser(User user);
    void changeRole(int id, int roleId);
    void changeSalary(int id, double salary);
    void deleteUser(int id);
}
