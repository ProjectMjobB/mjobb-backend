package com.mjobb.service;

import com.mjobb.entity.Category;
import com.mjobb.entity.Tag;
import com.mjobb.repository.CategoryRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories(Optional <String> title);

    void saveCategory(Category category);

    void deleteCategory(Category category);

    Category getCategoryById(Long id);

     Optional<List<Category>> getPopularCategories();

    Category getCategoriesById(Long id);


}