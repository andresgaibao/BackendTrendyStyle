package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Producto;
import com.proyecto.trendy.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto/*,
                                                   /*@RequestParam(value = "imagen",required = false)*/
            /*MultipartFile imagen*/) /*throws IOException */{
        Producto nuevoProducto = productoService.createProducto(producto /*, imagen*/);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Integer id,
                                                   @RequestBody Producto updatedProducto/*,
                                                   /*@RequestParam(value = "imagen", required = false)
                                                    MultipartFile updatedImagen*/) /*throws IOException*/ {
        Producto producto = productoService.updateProducto(id, updatedProducto/*, updatedImagen*/);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        productoService.deleteProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/mostrar/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer id) {
        Producto producto = productoService.getProductoById(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }
}
