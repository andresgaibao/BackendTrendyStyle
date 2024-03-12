package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Category;
import com.proyecto.trendy.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryService service) {
        this.service = service;

    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = service.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        Category category = service.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = service.createCategory(category.getName());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        Category updated = service.updateCategory(id, category.getName());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        service.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
