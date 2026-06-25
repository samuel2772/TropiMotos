/**
 * CustomUserDetailsService - Servicio de carga de usuarios para autenticacion
 * 
 * Implementa UserDetailsService de Spring Security para cargar
 * usuarios desde la base de datos durante la autenticacion.
 */
package com.tropimotos.security;

import com.tropimotos.entity.Usuario;
import com.tropimotos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Locale;

/**
 *@Service: Marca la clase como un componente de servicio de Spring
 *@RequiredArgsConstructor: Lombok - genera constructor con campos final
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /** Repositorio para acceder a datos de usuarios */
    private final UsuarioRepository usuarioRepository;

    /**
     * Carga un usuario por su email para autenticacion
     * Este metodo es usado automaticamente por Spring Security
     * 
     * @param email Email del usuario a buscar
     * @return UserDetails con la informacion del usuario
     * @throws UsernameNotFoundException si el usuario no existe
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailWithRol(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String rol = usuario.getRol().getNombre();
        String authority = "ROLE_" + rol.trim().toUpperCase(Locale.ROOT);

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(authority))
        );
    }
}
