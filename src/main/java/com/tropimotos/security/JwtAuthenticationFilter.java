/**
 * JwtAuthenticationFilter - Filtro de autenticacion JWT
 * 
 * Este filtro intercepta todas las solicitudes HTTP y valida
 * el token JWT en el header Authorization.
 */
package com.tropimotos.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 *@Component: Marca la clase como un componente de Spring (bean)
 *@RequiredArgsConstructor: Lombok - genera constructor con campos final
 *OncePerRequestFilter: Filtro que se ejecuta una vez por solicitud
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** Servicio JWT para extraer y validar tokens */
    private final JwtService jwtService;
    
    /** Servicio para cargar detalles del usuario */
    private final CustomUserDetailsService userDetailsService;

    /**
     * Metodo principal del filtro que procesa cada solicitud
     * Extrae el token JWT, lo valida y establece la autenticacion
     * 
     * @param request Solicitud HTTP entrante
     * @param response Respuesta HTTP
     * @param filterChain Cadena de filtros siguiente
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Obtiene el header Authorization
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Si no hay token Bearer, pasa al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrae el token (sin "Bearer ")
        jwt = authHeader.substring(7);

        try {
            // Extrae el email del usuario del token
            userEmail = jwtService.extractUsername(jwt);

            // Si tenemos email y no hay autenticacion actual
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Carga los detalles del usuario desde la BD
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Valida el token
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Crea el token de autenticacion
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,  // Credenciales (no necesarias porque ya se valido)
                            userDetails.getAuthorities()  // Roles del usuario
                    );
                    
                    // Establece detalles adicionales de la autenticacion
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Establece la autenticacion en el contexto de Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Si falla la autenticacion, solo registra el error y continua
            logger.error("No se pudo autenticar el token", e);
        }

        // Pasa la solicitud al siguiente filtro
        filterChain.doFilter(request, response);
    }
}
