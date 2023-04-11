package org.application.dao.impl;

import jakarta.persistence.EntityManager;
import org.application.dao.DAO;
import org.application.models.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CategoryDAOImpl implements DAO<Category> {
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(CategoryDAOImpl.class);
    public CategoryDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Category get(int id) {
        return em.find(Category.class, id);
    }

    @Override
    public List<Category> getAll() {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    @Override
    public Category create(Category category) {
        em.persist(category);
        return category;
    }

    @Override
    public void update(Category category) {
        em.merge(category);
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(Category.class, id));
    }
}
