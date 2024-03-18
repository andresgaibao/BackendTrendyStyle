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

    //Métodos adicionales
   /* public List<Product> getRelatedProducts(Integer category_id, Integer id){
        List<Product>  producList = this.repository.finbByCategoryAndIdNot(category_id, id);
        List<Product> randomProducts = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 2; i++){
            int randomIndex = random.nextInt(producList.size());
            randomProducts.add(producList.get(randomIndex));
            producList.remove(randomIndex);
        }
        return randomProducts;
    }

    public  List<Product> getBestPriceProducts(){
        return  this.repository.findFirstByOrderByPriceAsc();
    }*/

}
