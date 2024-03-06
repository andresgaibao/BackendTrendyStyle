package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.User;
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
import java.util.List;

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

    @GetMapping("/list")
    public List<User> getAllUsers() {
        return authenticationService.getAllUsers();
    }
    @PreAuthorize("isAuthenticated() and principal.username == #id.toString()")
    @PutMapping("/update/{userId}")
    public ResponseEntity<AuthenticationResponse> updateUser(@PathVariable Integer id,
                                                             @RequestBody RegisterRequest updatedUserData) {
        AuthenticationResponse response = service.updateUser(id, updatedUserData);
        return ResponseEntity.ok(response);
    }



    private final AuthenticationService authenticationService;

    //@GetMapping("/info")
    //public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal User user) {
        // Puedes usar el objeto User directamente desde el argumento del método
        //return ResponseEntity.ok(user);
   // }

    // Otro método para obtener información del usuario utilizando el servicio
    @GetMapping("/info")
    public ResponseEntity<User> getUserInfoFromService() {
        User currentUser = authenticationService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
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
