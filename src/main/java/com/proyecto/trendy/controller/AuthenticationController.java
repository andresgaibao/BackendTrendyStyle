package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.User;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.request.AuthenticationRequest;
import com.proyecto.trendy.request.RegisterRequest;
import com.proyecto.trendy.responses.AuthenticationResponse;
import com.proyecto.trendy.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws MyException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public List<User> getAllUsers() {

        return service.getAllUsers();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AuthenticationResponse> updateUser(@PathVariable Integer id,
                                                             @RequestBody RegisterRequest updatedUserData) {
        AuthenticationResponse response = service.updateUser(id, updatedUserData);
        return ResponseEntity.ok(response);
    }




    // Endpoint para obtener información del usuario utilizando el servicio
    @GetMapping("/info")
    public ResponseEntity<User> getUserInfoFromService() {
        User currentUser = service.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }

    //Endpoint para obtener la informacion del usuario segun su id.
    @GetMapping("/info/{id}")
    public ResponseEntity<User> getUserInfoById(@PathVariable Integer id) {
        User user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        service.logout(request, response);
        return ResponseEntity.ok("Logout successful");
    }
}
