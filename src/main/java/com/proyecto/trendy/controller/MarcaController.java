package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.services.MarcasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/marcas")
public class MarcaController {

    private final MarcasService marcaService;

    @Autowired
    public MarcaController(MarcasService marcaService) {
        this.marcaService = marcaService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<Marca> createMarca(@RequestBody Marca marca/*, /*MultipartFile logo*/) throws IOException {
        Marca createdMarca = marcaService.createMarca(marca/*, logo*/);
        return ResponseEntity.ok(createdMarca);
        //return ResponseEntity.ok("Marca creada exitosamente");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Marca> updateMarca(@PathVariable Long id, @RequestBody Marca updatedMarca/*, MultipartFile logo*/) {
        Marca updated = marcaService.updateMarca(id, updatedMarca/*, logo*/);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteMarca(@PathVariable Long id) {
        marcaService.deleteMarca(id);
        return ResponseEntity.ok("Marca eliminada exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> getMarcaById(@PathVariable Long id) {
        Marca marca = marcaService.getMarcaById(id);
        return ResponseEntity.ok(marca);
    }

}
