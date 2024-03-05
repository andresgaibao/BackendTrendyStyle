package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Producto;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    private final ProductoService service;

    @Autowired
    public ProductoController(ProductoService service) {
        this.service = service;
    }


    @PostMapping("/registrar")
    public ResponseEntity<String> registrarProducto(@RequestParam("archivo") MultipartFile archivo,
                                                    @RequestParam("name")  String name,
                                                    @RequestParam("precio")  Double precio,
                                                    @RequestParam("marcaId")  Integer marcaId) {
        try {
            service.registrarProducto(archivo, name, precio, marcaId);
            return new ResponseEntity<>("Producto registrado correctamente", HttpStatus.CREATED);
        } catch (MyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarProducto(@PathVariable("id") Integer id,
                                                     @RequestParam("archivo") MultipartFile archivo,
                                                     @RequestParam("name")  String name,
                                                     @RequestParam("precio")  Double precio,
                                                     @RequestParam("marcaId")  Integer marcaId) {
        try {
            service.actualizarProducto(id, archivo, name, precio, marcaId);
            return new ResponseEntity<>("Producto actualizado correctamente", HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable("id") Integer id) {
        try {
            Producto producto = service.buscarProducto(id);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = service.listarProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable("id") Integer id) {
        try {
            service.eliminarProducto(id);
            return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

