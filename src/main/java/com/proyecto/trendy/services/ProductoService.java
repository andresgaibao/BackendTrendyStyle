package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.Category;
import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.entity.Product;
import com.proyecto.trendy.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductRepository repository;


    public Product createProduct(String name, BigDecimal price, MultipartFile img, Marca marca_id, String detail,
                                 Category category_id) throws IOException {

        // Validar campos obligatorios
        if (name == null || name.trim().isEmpty() || price == null || img == null || img.isEmpty() || marca_id == null || category_id == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios para crear un producto");
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setImg(img.getBytes());
        product.setMarca(marca_id);
        product.setDetail(detail);
        product.setCategory(category_id);

        return repository.save(product);
    }

    public Product updateProduct(Integer id, String name, BigDecimal price, MultipartFile img, Marca marca_id,
                                 String detail, Category category_id) throws IOException {
        // Validar campos obligatorios
        if (name == null || name.trim().isEmpty() || price == null || img == null || img.isEmpty() || marca_id == null || category_id == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios para actualizar un producto");
        }

        Product existingProduct = getProductById(id);
        existingProduct.setName(name);
        existingProduct.setPrice(price);
        existingProduct.setImg(img.getBytes());
        existingProduct.setMarca(marca_id);
        existingProduct.setDetail(detail);
        existingProduct.setCategory(category_id);

        return repository.save(existingProduct);
    }

    public Product getProductById(Integer id) {
        Optional<Product> optionalProduct = repository.findById(id);
        return optionalProduct.orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }


    public Product obtenerProductoPorId(Integer id) {
        Optional<Product> productoOpcional = repository.findById(id);
        return productoOpcional.orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con el ID: " + id));
    }

    public List<Product> getAllProducts() {
        List<Product> productos = repository.findAll();
        if (productos.isEmpty()) {
            throw new EmptyResultDataAccessException("No se encontraron productos registrados", 0);
        }
        return productos;
    }

    public void deleteProduct(Integer id) {
        Product existingProduct = getProductById(id);
        repository.delete(existingProduct);
    }



}
