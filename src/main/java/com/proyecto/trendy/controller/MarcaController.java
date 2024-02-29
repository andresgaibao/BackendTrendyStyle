package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.services.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @PostMapping("/guardar")
    public ResponseEntity<Marca> registrarMarca(@RequestParam("archivo") MultipartFile archivo,
                                              @RequestParam("name") String name) {
        try {
            Marca nuevaMarca = marcaService.registrarMarca(archivo, name);
            return new ResponseEntity<>(nuevaMarca, HttpStatus.CREATED);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> buscarMarcaPorId(@PathVariable Integer id) {
        return marcaService.buscarMarcaPorId(id)
                .map(marca -> new ResponseEntity<>(marca, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Marca>> mostarMarcas() {
        List<Marca> marcas = marcaService.mostrarMarcas();
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Marca> actualizarMarca(@PathVariable Integer id,
                                                 @RequestParam("archivo") MultipartFile archivo,
                                                 @RequestParam("name") String name) {
        try {
            Marca marcaActualizada = marcaService.actualizarMarca(id, archivo, name);
            return new ResponseEntity<>(marcaActualizada, HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarMarca(@PathVariable Integer id) {
        marcaService.eliminarPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
