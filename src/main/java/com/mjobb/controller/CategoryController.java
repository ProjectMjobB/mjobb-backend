package com.mjobb.controller;

import com.mjobb.entity.Category;
import com.mjobb.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1.0/categories/")
@RequiredArgsConstructor
@ApiOperation("Categories API")
public class CategoryController {


    private final CategoryService categoryService;


    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam(required = false) String title) {

        return new ResponseEntity<>(categoryService.getAllCategories(Optional.ofNullable(title)), HttpStatus.OK);
    }

    @PostMapping("/save")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoriesById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(categoryService.getCategoriesById(id), HttpStatus.OK);
    }

    @PostMapping("/delete")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteCategory(@RequestBody Category category) {
        categoryService.deleteCategory(category);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularCategories(){
        return ResponseEntity.ok(categoryService.getPopularCategories());
    }

}
