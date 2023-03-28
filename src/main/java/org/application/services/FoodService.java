package org.application.services;

import org.application.models.Food;

import java.util.List;

public interface FoodService {
    Food createFood(Food food);
    void updateFood(Food food);
    void deleteFood(int id);
    Food getFood(int id);
    List<Food> getAllFood();
}
