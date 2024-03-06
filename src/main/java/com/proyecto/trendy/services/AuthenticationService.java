package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.User;
import com.proyecto.trendy.enums.Role;
import com.proyecto.trendy.repository.UserRepository;
import com.proyecto.trendy.request.AuthenticationRequest;
import com.proyecto.trendy.request.RegisterRequest;
import com.proyecto.trendy.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }


    @PreAuthorize("#userId == authentication.principal.id")
    public AuthenticationResponse updateUser(Integer id, RegisterRequest updatedUserData) {
        var user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Actualiza los datos del usuario con la nueva información
        user.setName(updatedUserData.getName());
        user.setEmail(updatedUserData.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUserData.getPassword()));
        // Puedes decidir si actualizar la contraseña o no

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    // Método para obtener la información del usuario actualmente autenticado
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        } else {
            throw new RuntimeException("No se pudo obtener la información del usuario actual");
        }
    }

    // Método para eliminar un usuario
    @PreAuthorize("#userEmail == authentication.principal.username")
    public void deleteUser(String userEmail) {
        var user = repository.findByEmail(userEmail)
                .orElseThrow();

        repository.delete(user);
    }

    // Método para cerrar sesion
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalida la sesión actual
        SecurityContextHolder.clearContext();

        // Elimina la cookie de autenticación
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
}
