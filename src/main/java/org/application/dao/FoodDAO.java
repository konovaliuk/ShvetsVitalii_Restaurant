package org.application.dao;

import org.application.models.Food;

public interface FoodDAO extends DAO<Food>{
    Food getByName(String name);
}
