package com.mjobb.service.impl;

import com.mjobb.entity.Category;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.CategoryRepository;
import com.mjobb.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private static final int LIMIT = 8;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }



    @Override
    public Optional<List<Category>> getPopularCategories() {
        Page<Category> page = categoryRepository.findAll(PageRequest.of(0, LIMIT, Sort.by(Sort.Order.asc("id"))));
        return Optional.of(page.getContent());
    }

    @Override
    public void saveTag(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new WebServiceException("Category not found this id: " + id));
    }
}