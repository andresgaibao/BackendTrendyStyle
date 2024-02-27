package com.proyecto.trendy.controller;

import com.proyecto.trendy.request.AuthenticationRequest;
import com.proyecto.trendy.request.RegisterRequest;
import com.proyecto.trendy.responses.AuthenticationResponse;
import com.proyecto.trendy.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return  ResponseEntity.ok(service.authenticate(request));
    }

    @PreAuthorize("isAuthenticated() and principal.username == #email")
    @PutMapping("/update/{email}")
    public ResponseEntity<AuthenticationResponse> updateUser(@PathVariable String email,
                                                             @RequestBody RegisterRequest updatedUserData) {
        AuthenticationResponse response = service.updateUser(email, updatedUserData);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated() and principal.username == #email")
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        service.deleteUser(email);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        service.logout(request, response);
        return ResponseEntity.ok("Logout successful");
    }
}