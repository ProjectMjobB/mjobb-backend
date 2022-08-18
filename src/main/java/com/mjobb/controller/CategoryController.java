package com.mjobb.controller;

import com.mjobb.entity.Category;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1.0/categories/")
@RequiredArgsConstructor
@ApiOperation("Categories API")
public class CategoryController {


    private final CategoryService categoryService;


    @GetMapping("/all")
    public ResponseEntity<List<Category>> allTags() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/save")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Category> saveTag(@RequestBody Category category) {
        categoryService.saveTag(category);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteTag(@RequestBody Category category) {
        categoryService.deleteCategory(category);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getJobsFromCategory(@RequestParam Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category.getJobAdvertisements());
    }
}
