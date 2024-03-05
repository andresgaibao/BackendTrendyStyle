package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Venta;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    @Autowired
    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarVenta(
            @RequestParam Integer userId,
            @RequestParam List<Integer> productoIds,
            @RequestParam Double total) {
        try {
            ventaService.registrarVenta(userId, productoIds, total);
            return ResponseEntity.status(HttpStatus.CREATED).body("Venta registrada exitosamente.");
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarVenta(
            @PathVariable Integer id,
            @RequestParam Integer userId,
            @RequestParam List<Integer> productoIds,
            @RequestParam Double total) {
        try {
            ventaService.actualizarVenta(id, userId, productoIds, total);
            return ResponseEntity.status(HttpStatus.OK).body("Venta actualizada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.listarVentas();
        return ResponseEntity.status(HttpStatus.OK).body(ventas);
    }

    @GetMapping("/leer/{id}")
    public ResponseEntity<Venta> leerVenta(@PathVariable Integer id) {
        try {
            Venta venta = ventaService.leerVenta(id);
            return ResponseEntity.status(HttpStatus.OK).body(venta);
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Integer id) {
        try {
            ventaService.eliminarVenta(id);
            return ResponseEntity.status(HttpStatus.OK).body("Venta eliminada exitosamente.");
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
