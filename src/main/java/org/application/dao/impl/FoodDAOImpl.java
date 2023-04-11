package org.application.dao.impl;

import jakarta.persistence.EntityManager;
import org.application.dao.FoodDAO;
import org.application.models.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FoodDAOImpl implements FoodDAO {
    private static final Logger logger = LoggerFactory.getLogger(FoodDAOImpl.class);
    private final EntityManager em;

    public FoodDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public Food get(int id) {
        return em.find(Food.class, id);
    }

    @Override
    public List<Food> getAll() {
        return em.createQuery("SELECT f FROM Food f", Food.class).getResultList();
    }

    @Override
    public Food getByName(String name) {
        return em.createQuery("SELECT f FROM Food f WHERE f.name = :name", Food.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public Food create(Food food) {
           em.persist(food);
            return food;
    }

    @Override
    public void update(Food food) {
        em.merge(food);
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(Food.class, id));
    }
}
