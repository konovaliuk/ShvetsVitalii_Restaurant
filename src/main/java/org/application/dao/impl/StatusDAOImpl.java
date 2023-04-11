package org.application.dao.impl;

import jakarta.persistence.EntityManager;
import org.application.dao.StatusDAO;
import org.application.models.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StatusDAOImpl implements StatusDAO{
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(StatusDAOImpl.class);
    public StatusDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Status get(int id) {
        return em.find(Status.class, id);
    }

    @Override
    public List<Status> getAll() {
        return em.createQuery("SELECT s FROM Status s", Status.class).getResultList();
    }

    @Override
    public Status create(Status status) {
        em.persist(status);
        return status;
    }

    @Override
    public void update(Status status) {
        em.merge(status);
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(Status.class, id));
    }
}
