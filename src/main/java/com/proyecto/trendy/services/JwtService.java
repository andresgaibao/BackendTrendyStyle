package com.proyecto.trendy.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    // Clave secreta utilizada para firmar y verificar los tokens JWT.
    private static final String SECRET_KEY =  "735d9d376b7edb0fc98859747065c5c4ea2b4047245d6fcc434e5b6e4ad31cd5";

    // Método para extraer el nombre de usuario desde un token JWT.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Método genérico para extraer cualquier reclamo (claim) de un token JWT.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Método para generar un token JWT a partir de los detalles del usuario.
    public  String generateToken(UserDetails userDetails){
        return  generateToken(new HashMap<>(), userDetails);
    }

    // Método para generar un token JWT con reclamos adicionales y detalles del usuario.
    public String generateToken(
            Map<String, Objects> extraClaims,
            UserDetails userDetails
    ){
        return  Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    // Método para verificar la validez de un token JWT para un usuario dado.
    public  boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Método privado para verificar si un token JWT ha expirado.
    private boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }

    // Método privado para extraer la fecha de expiración de un token JWT.
    private Date extractExpiration(String token) {
        return  extractClaim(token, Claims::getExpiration);
    }

    // Método privado para extraer todos los reclamos de un token JWT.
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método privado para obtener la clave de firma a partir de la clave secreta
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
