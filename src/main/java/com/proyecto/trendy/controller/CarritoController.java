package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Carrito;
import com.proyecto.trendy.entity.CartItem;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.repository.CarritoRepository;
import com.proyecto.trendy.responses.Response;
import com.proyecto.trendy.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private final CarritoService service;

    public CarritoController(CarritoService service, CarritoRepository itemRepo) {
        this.service = service;
        this.itemRepo = itemRepo;
    }

    @PostMapping("/addcart/{id}/{cantidad}")
    public ResponseEntity<?> saveToCart(@PathVariable Integer id, @PathVariable int cantidad) throws MyException {
        try {
            service.addProduct(id, cantidad);
            Response addResponse = new Response("Se agrego el producto al carrito");
            return new ResponseEntity(addResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(e.getMessage() + "No se pudo conectar a la base de datos");
        }

    }

    @Autowired
    private final CarritoRepository itemRepo;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFromCart(@PathVariable Integer id) throws MyException {
        try {
            Optional<Carrito> searchItem = itemRepo.findById(id);
            if (searchItem != null) {

                service.deleteFromCart(id);
                Response deleteResponse = new Response("Producto eliminado del carrito");
                return new ResponseEntity<>(deleteResponse, HttpStatus.OK);

            } else {
                Response deleteResponse = new Response("No se encontró el producto en los ítems");
                return new ResponseEntity<>(deleteResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new MyException(e.getMessage() + "Error al acceder a la base de datos");
        }

    }
}















