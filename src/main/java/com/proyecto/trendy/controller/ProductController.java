package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Category;
import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.entity.Product;
import com.proyecto.trendy.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductoService productService;

    @Autowired
    public ProductController(ProductoService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestParam String name, @RequestParam Double price,
                                                 @RequestParam MultipartFile img, @RequestParam Marca marca_id,
                                                 @RequestParam String detail, @RequestParam Category category_id) {
        try {
            Product createdProduct = productService.createProduct(name, price, img, marca_id, detail, category_id);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestParam String name,
                                                 @RequestParam Double price, @RequestParam MultipartFile img,
                                                 @RequestParam Marca marca_id, @RequestParam String detail,
                                                 @RequestParam Category category_id) {
        try {
            Product updatedProduct = productService.updateProduct(id, name, price, img, marca_id, detail, category_id);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (IOException
                e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        try {
            Product product = productService.getProductById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
