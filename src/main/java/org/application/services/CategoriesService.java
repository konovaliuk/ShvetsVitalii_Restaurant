package org.application.services;

import org.application.models.Category;

import java.util.List;

public interface CategoriesService {
    Category createCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(int id);
    Category getCategory(int id);
    List<Category> getAllCategory();
}
