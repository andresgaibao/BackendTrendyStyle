package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.Category;
import com.proyecto.trendy.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service

public class CategoryService {
    private final  CategoryRepository repository;
    @Autowired
    public CategoryService(CategoryRepository repository, CategoryRepository respository) {
        this.repository = repository;
    }

    public Category createCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo o vacío");
        }
        Category category = new Category();
        category.setName(categoryName);
        return repository.save(category);
    }


    public Category updateCategory(Integer id, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo o vacío");
        }
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(name);
        return repository.save(existingCategory);
    }

    public Category getCategoryById(Integer id) {
        Optional<Category> optionalCategory = repository.findById(id);
        return optionalCategory.orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con el ID: " + id));
    }


    public List<Category> getAllCategories() {
        return repository.findAll();
    }


    public void deleteCategory(Integer id) {
        Category existingCategory = getCategoryById(id);
        repository.delete(existingCategory);
    }


}
