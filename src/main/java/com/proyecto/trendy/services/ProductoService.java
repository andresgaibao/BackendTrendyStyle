package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.Category;
import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.entity.Product;
import com.proyecto.trendy.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductRepository repository;


    public Product createProduct(String name, Double price, MultipartFile img, Marca marca_id, String detail,
                                 Category category_id) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setImg(img.getBytes());
        product.setMarca(marca_id);
        product.setDetail(detail);
        product.setCategory(category_id);

        return repository.save(product);
    }

    public Product updateProduct(Integer id, String name, Double price, MultipartFile img, Marca marca_id,
                                 String detail, Category category_id) throws IOException {
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

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void deleteProduct(Integer id) {
        Product existingProduct = getProductById(id);
        repository.delete(existingProduct);
    }

}
