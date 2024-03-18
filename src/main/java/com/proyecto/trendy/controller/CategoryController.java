package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Category;
import com.proyecto.trendy.exceptions.MyException;
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
    public CategoryController( CategoryService service) {
        this.service = service;

    }

    /**
     * Obtiene todas las categorías.
     *
     * @return Lista de categorías.
     * @throws MyException si ocurre un error al acceder a la base de datos.
     */

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() throws MyException {
        try {
            List<Category> categories = service.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException("Error al obtener todas las categorías"+ e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        Category category = service.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * Crea una nueva categoría.
     *
     * @param category La categoría a crear.
     * @return La categoría creada.
     */
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {

        Category createdCategory = service.createCategory(category.getName());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }


    /**
     * Actualiza una categoría existente.
     *
     * @param id       ID de la categoría a actualizar.
     * @param category La nueva información de la categoría.
     * @return La categoría actualizada.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        Category updated = service.updateCategory(id, category.getName());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param id ID de la categoría a eliminar.
     * @return ResponseEntity sin contenido.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        service.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
