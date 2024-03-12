package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.InfoUser;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.services.InfoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/infoUsers")
@RequiredArgsConstructor
public class InfoUserController {

    private final InfoUserService infoUserService;

    @PostMapping("/registrar")
    public ResponseEntity<InfoUser> registrarInfoUser(@RequestBody InfoUser infoUser) {
        try {
            InfoUser savedInfoUser = infoUserService.registrarInfoUser(
                    infoUser.getLastname(),
                    infoUser.getCc(),
                    infoUser.getNum_cel(),
                    infoUser.getCity_of_residence(),
                    infoUser.getAddress()

            );
            return new ResponseEntity<>(savedInfoUser, HttpStatus.CREATED);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<InfoUser> actualizarInfoUser(@PathVariable Integer id, @RequestBody InfoUser infoUser) {
        try {
            InfoUser updatedInfoUser = infoUserService.actualizarInfoUser(
                    id,
                    infoUser.getLastname(),
                    infoUser.getCc(),
                    infoUser.getNum_cel(),
                    infoUser.getCity_of_residence(),
                    infoUser.getAddress(),

            );
            if (updatedInfoUser != null) {
                return new ResponseEntity<>(updatedInfoUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<InfoUser> buscarInfoUserPorId(@PathVariable Integer id) {
        Optional<InfoUser> infoUser = infoUserService.buscarInfoUserPorId(id);
        return infoUser.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/mostrar")
    public ResponseEntity<List<InfoUser>> mostrarInfoUsers() {
        List<InfoUser> infoUsers = infoUserService.mostrarInfoUsers();
        return new ResponseEntity<>(infoUsers, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteInfoUser(@PathVariable Integer id) {
        try {
            infoUserService.deleteInfoUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
