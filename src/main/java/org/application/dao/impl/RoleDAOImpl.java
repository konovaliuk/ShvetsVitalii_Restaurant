package org.application.dao.impl;

import jakarta.persistence.EntityManager;
import org.application.dao.RoleDAO;
import org.application.models.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RoleDAOImpl implements RoleDAO {
   private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);
    public RoleDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Role get(int id) {
        return em.find(Role.class, id);
    }

    @Override
    public List<Role> getAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role create(Role role) {
        em.persist(role);
        return role;
    }

    @Override
    public void update(Role role) {
        em.merge(role);
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(Role.class, id));
    }
}
