package org.application.services.impl;


import jakarta.transaction.Transactional;
import org.application.models.Category;
import org.application.repository.CategoryRepository;
import org.application.services.CategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoryRepository categoriesRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoriesServiceImpl.class);
    @Autowired
    public CategoriesServiceImpl(CategoryRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }
    @Override
    public Category createCategory(Category category) {
        return categoriesRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoriesRepository.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoriesRepository.deleteById(id);
    }

    @Override
    public Category getCategory(int id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoriesRepository.findAll();
    }
}
