package com.mjobb.service.impl;

import com.mjobb.entity.Category;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.CategoryRepository;
import com.mjobb.repository.JobAdvertisementRepository;
import com.mjobb.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final JobAdvertisementRepository jobAdvertisementRepository;
    private static final int LIMIT = 8;

    @Override
    public List<Category> getAllCategories(Optional <String> title) {

        List<Category> categories = new ArrayList<>();
        if (title.isEmpty())
            categoryRepository.findAll().forEach(categories::add);
        else
            categoryRepository.findByNameContaining(String.valueOf(title)).forEach(categories::add);
        if (categories.isEmpty()) {
            return null;
        }
        return categories;
    }

    @Override
    public Optional<List<Category>> getPopularCategories() {
        Page<Category> page = categoryRepository.findAll(PageRequest.of(0, LIMIT, Sort.by(Sort.Order.asc("id"))));
        return Optional.of(page.getContent());
    }

    @Override
    public Category getCategoriesById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("Not found Tag with id = " + id));
        return category;
    }

    @Override
    public void saveCategory(Category category) {
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