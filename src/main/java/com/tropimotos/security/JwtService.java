/**
 * JwtService - Servicio para manipulacion de tokens JWT
 * 
 * Proporciona metodos para generar y validar tokens JWT
 * utilizados en la autenticacion del sistema.
 */
package com.tropimotos.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.tropimotos.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *@Service: Marca la clase como un componente de servicio de Spring
 */
@Service
public class JwtService {

    /** Clave secreta para firmar los tokens (definida en application.properties) */
    @Value("${jwt.secret}")
    private String secretKey;

    /** Tiempo de expiracion del token en milisegundos */
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Extrae el nombre de usuario (email) del token
     * 
     * @param token Token JWT
     * @return Email del usuario
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae un claim especifico del token
     * 
     * @param <T> Tipo del claim a extraer
     * @param token Token JWT
     * @param claimsResolver Funcion para extraer el claim
     * @return Valor del claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un token JWT para un usuario
     * Incluye claims personalizados como idUsuario, rol y nombre
     * 
     * @param usuario Usuario para el cual generar el token
     * @return Token JWT generado
     */
    public String generateToken(Usuario usuario) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("idUsuario", usuario.getIdUsuario());
        extraClaims.put("rol", usuario.getRol().getNombre());
        extraClaims.put("nombre", usuario.getNombre());
        return generateToken(extraClaims, usuario.getEmail());
    }

    /**
     * Genera un token JWT con claims personalizados
     * 
     * @param extraClaims Claims adicionales a incluir
     * @param username Nombre de usuario (email)
     * @return Token JWT generado
     */
    public String generateToken(Map<String, Object> extraClaims, String username) {
        return buildToken(extraClaims, username, jwtExpiration);
    }

    /**
     * Construye el token JWT con todos sus componentes
     * 
     * @param extraClaims Claims adicionales
     * @param username Nombre de usuario
     * @param expiration Tiempo de expiracion
     * @return Token JWT construido
     */
    private String buildToken(Map<String, Object> extraClaims, String username, long expiration) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Valida si un token es valido
     * Verifica que el usuario coincida y el token no haya expirado
     * 
     * @param token Token JWT a validar
     * @param userDetails Detalles del usuario
     * @return true si el token es valido
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica si el token ha expirado
     * 
     * @param token Token JWT
     * @return true si ha expirado
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiracion del token
     * 
     * @param token Token JWT
     * @return Fecha de expiracion
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae todos los claims del token
     * 
     * @param token Token JWT
     * @return Claims contenidos en el token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Obtiene la clave de firma decodificada
     * 
     * @return Clave para verificar la firma del token
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
