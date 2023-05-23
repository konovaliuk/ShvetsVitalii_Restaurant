package org.application.services.impl;

import jakarta.transaction.Transactional;
import org.application.models.User;
import org.application.repository.UserRepository;
import org.application.services.RoleService;
import org.application.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getAllUsers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> getUsersByRole(String roleName) {
        return userRepository.findByRole(roleName);
    }
    @Override
    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public User registerUser(User user) {
        user.setRole(roleService.getRoleByName("customer"));
        user.setSalary(0);
        if (userRepository.findByLogin(user.getLogin()) != null || userRepository.findByEmail(user.getEmail()) != null) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public User loginUser(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }
    @Override
    public void changeRole(int id, int roleId) {
        User user = userRepository.findById(id).orElse(null);
        if (user!=null && !user.getRole().getName().equals("admin")) {
            user.setRole(roleService.getRole(roleId));
            userRepository.save(user);
        }
    }
    @Override
    public void changeSalary(int id, double salary) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setSalary(salary);
            userRepository.save(user);
        }
    }
    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
