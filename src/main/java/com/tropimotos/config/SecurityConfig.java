/**
 * SecurityConfig - Configuracion de seguridad de Spring
 * 
 * Define las reglas de seguridad, filtros de autenticacion
 * y manejo de CORS para la aplicacion.
 */
package com.tropimotos.config;

import com.tropimotos.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 *@Configuration: Marca la clase como fuente de configuracion de Spring
 *@EnableWebSecurity: Habilita la seguridad web de Spring Security
 *@RequiredArgsConstructor: Lombok - genera constructor con campos final
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /** Filtro JWT de autenticacion */
    private final JwtAuthenticationFilter jwtAuthFilter;
    
    /** Servicio para cargar detalles del usuario */
    private final UserDetailsService userDetailsService;

    /**
     * Configura la cadena de filtros de seguridad
     * Define que endpoints son publicos y cuales requieren autenticacion
     * 
     * @param http Configurador de seguridad HTTP
     * @return SecurityFilterChain configurado
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configuracion de CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Deshabilita CSRF (no necesario con JWT)
                .csrf(AbstractHttpConfigurer::disable)
                // Configura las reglas de autorizacion
                .authorizeHttpRequests(auth -> auth
                        // Endpoints de autenticacion son publicos
                        .requestMatchers("/api/auth/**").permitAll()
                        // Endpoints de actuator son publicos
                        .requestMatchers("/actuator/**").permitAll()
                        // Cualquier otra solicitud requiere autenticacion
                        .anyRequest().authenticated()
                )
                // Configura la gestion de sesiones como stateless (sin estado)
                // No se guardan sesiones en el servidor
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Configura el proveedor de autenticacion
                .authenticationProvider(authenticationProvider())
                // Agrega el filtro JWT antes del filtro de autenticacion
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura el origen CORS permitido
     * Permite solicitudes desde cualquier origen con metodos comunes
     * 
     * @return Configuracion CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite todos los origines (en produccion especificar dominios)
        configuration.setAllowedOrigins(List.of("*"));
        // Permite todos los metodos HTTP comunes
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permite todos los headers
        configuration.setAllowedHeaders(List.of("*"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configura el proveedor de autenticacion
     * Usa DaoAuthenticationProvider que carga usuarios desde la BD
     * 
     * @return AuthenticationProvider configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Establece el servicio que carga usuarios
        authProvider.setUserDetailsService(userDetailsService);
        // Establece el codificador de contrasenas
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Expone el AuthenticationManager como bean
     * 
     * @param config Configuracion de autenticacion
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configura el codificador de contrasenas
     * Usa BCrypt que es un algoritmo seguro de hash
     * 
     * @return PasswordEncoder configurado
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
