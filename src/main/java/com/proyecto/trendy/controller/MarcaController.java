package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.services.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService service;

    @PostMapping("/guardar")
    public ResponseEntity<Marca> registrarMarca(@RequestParam("img") MultipartFile img,
                                                @RequestParam("name") String name) {
        try {
            // Validar campos vacíos
            if (StringUtils.isEmpty(name) || img.isEmpty()) {
                throw new MyException("Nombre y archivo son campos obligatorios");
            }

            // Resto del código para guardar en la base de datos
            Marca nuevaMarca = service.registrarMarca(img, name);

            return new ResponseEntity<>(nuevaMarca, HttpStatus.CREATED);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            // Manejar otras excepciones aquí
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Marca> actualizarMarca(@PathVariable Integer id,
                                                 @RequestParam("img") MultipartFile img,
                                                 @RequestParam("name") String name) {
        try {
            Marca marcaActualizada = service.actualizarMarca(id, img, name);
            return new ResponseEntity<>(marcaActualizada, HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Marca>> mostarMarcas() {
        List<Marca> marcas = service.mostrarMarcas();
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Integer id) {
        service.deleteMarca(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
