package com.mjobb.service;

import com.mjobb.entity.Category;
import com.mjobb.entity.Tag;
import com.mjobb.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();

    void saveTag(Category category);

    void deleteCategory(Category category);

    Category getCategoryById(Long id);

     Optional<List<Category>> getPopularCategories();

}