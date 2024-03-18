package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.responses.Response;
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

    /**
     * Crea una nueva marca.
     *
     * @param img   Imagen de la marca.
     * @param name  Nombre de la marca.
     * @return La marca creada.
     */
    @PostMapping("/guardar")
    public ResponseEntity<Marca> registrarMarca(@RequestParam("img") MultipartFile img,
                                                @RequestParam("name") String name) {
        try {
            // Validar campos
            if (name == null || name.trim().isEmpty() || img.isEmpty()) {
                throw new MyException("El nombre y la imagen de la marca son campos obligatorios");
            }

            Marca nuevaMarca = service.registrarMarca(img, name);

            return new ResponseEntity<>(nuevaMarca, HttpStatus.CREATED);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Actualiza una marca existente.
     *
     * @param id    ID de la marca a actualizar.
     * @param img   Nueva imagen de la marca.
     * @param name  Nuevo nombre de la marca.
     * @return La marca actualizada.
     */
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

    /**
     * Obtiene todas las marcas.
     *
     * @return Lista de marcas.
     * @throws MyException si ocurre un error al acceder a la base de datos.
     */
    @GetMapping("/todas")
    public ResponseEntity<List<Marca>> mostarMarcas() throws MyException {
        List<Marca> marcas = service.mostrarMarcas();
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    /**
     * Obtiene una marca por su ID.
     *
     * @param id ID de la marca.
     * @return La marca con el ID especificado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Marca> getMarcaById(@PathVariable Integer id) {
        Marca marca = service.getMarcaById(id);
        return new ResponseEntity<>(marca, HttpStatus.OK);
    }

    /**
     * Elimina una marca por su ID.
     *
     * @param id ID de la marca a eliminar.
     * @return ResponseEntity sin contenido.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteMarca(@PathVariable Integer id) {
        try {
            service.deleteMarca(id);
            Response deleteResponse = new Response("Marca eliminada con exito");
            return new ResponseEntity<>(deleteResponse, HttpStatus.NO_CONTENT);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
