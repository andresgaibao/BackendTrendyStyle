package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Category;
import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.entity.Product;
import com.proyecto.trendy.responses.Response;
import com.proyecto.trendy.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductoService productService;

    @Autowired
    public ProductController(ProductoService productService) {
        this.productService = productService;
    }

    /**
     * Crea un nuevo producto.
     *
     * @param name        Nombre del producto.
     * @param price       Precio del producto.
     * @param img         Imagen del producto.
     * @param marca_id    ID de la marca del producto.
     * @param detail      Detalle del producto.
     * @param category_id ID de la categoría del producto.
     * @return El producto creado.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestParam String name, @RequestParam BigDecimal price,
                                                 @RequestParam MultipartFile img, @RequestParam Marca marca_id,
                                                 @RequestParam String detail, @RequestParam Category category_id) {
        try {
            Product createdProduct = productService.createProduct(name, price, img, marca_id, detail, category_id);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            Response errorResponse = new Response("Error al crear el producto");
            return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id          ID del producto a actualizar.
     * @param name        Nuevo nombre del producto.
     * @param price       Nuevo precio del producto.
     * @param img         Nueva imagen del producto.
     * @param marca_id    Nueva ID de la marca del producto.
     * @param detail      Nuevo detalle del producto.
     * @param category_id Nueva ID de la categoría del producto.
     * @return El producto actualizado.
     */
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestParam String name,
                                                 @RequestParam BigDecimal price, @RequestParam MultipartFile img,
                                                 @RequestParam Marca marca_id, @RequestParam String detail,
                                                 @RequestParam Category category_id) {
        try {
            Product updatedProduct = productService.updateProduct(id, name, price, img, marca_id, detail, category_id);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (IOException
                e) {
            Response errorResponse = new Response("Error al actualizar el producto");
            return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id ID del producto a obtener.
     * @return El producto encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        try {
            Product product = productService.getProductById(id);
            return new ResponseEntity<>( product, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Response notFoundResponse = new Response("Producto no encontrado");
            return new ResponseEntity<>(notFoundResponse,HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtiene todos los productos.
     *
     * @return La lista de todos los productos.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id ID del producto a eliminar.
     * @return ResponseEntity sin contenido.
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
            Response deleteProduct = new Response("Producto eliminado con exito");
            return new ResponseEntity<>(deleteProduct,HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            Response notFoundResponse = new Response("Producto no encontrado");
            return new ResponseEntity<>(notFoundResponse,HttpStatus.NOT_FOUND);
        }
    }
}
