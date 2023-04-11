package org.application.dao.impl;

import jakarta.persistence.EntityManager;
import org.application.dao.UserDAO;
import org.application.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    public UserDAOImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public List<User> getByRole(User user) {
        String name = user.getRole().getName();
        return em.createQuery("SELECT u FROM User u WHERE u.role.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public User getByLogin(String login) {
        return em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
    }

    @Override
    public User create(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(User.class, id));
    }
}
