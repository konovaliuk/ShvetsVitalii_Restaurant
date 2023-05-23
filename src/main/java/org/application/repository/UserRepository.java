package org.application.repository;

import org.application.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.role.name = ?1")
    List<User> findByRole(String roleName);
    @Query("select u from User u where u.login = ?1")
    User findByLogin(String login);
    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);
}
