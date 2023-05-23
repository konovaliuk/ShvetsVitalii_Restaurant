package org.application.services.impl;

import jakarta.transaction.Transactional;
import org.application.models.Food;
import org.application.repository.FoodRepository;
import org.application.services.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);
    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }
    @Override
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }
    @Override
    public void updateFood(Food food) {
        foodRepository.save(food);
    }
    @Override
    public void deleteFood(int id) {
        foodRepository.deleteById(id);
    }
    @Override
    public Food getFood(int id) {
        return foodRepository.findById(id).orElse(null);
    }
    @Override
    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public Page<Food> getAllFood(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        return foodRepository.findAll(pageable);
    }
}
