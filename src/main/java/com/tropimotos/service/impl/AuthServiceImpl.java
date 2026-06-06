/**
 * Implementacion del servicio de autenticacion
 * 
 * Esta clase implementa la interfaz AuthService y proporciona
 * la logica de negocio para login y registro de usuarios.
 */
package com.tropimotos.service.impl;

import com.tropimotos.dto.AuthResponse;
import com.tropimotos.dto.LoginRequest;
import com.tropimotos.dto.RegistroRequest;
import com.tropimotos.dto.UsuarioDTO;
import com.tropimotos.entity.Rol;
import com.tropimotos.entity.Usuario;
import com.tropimotos.repository.RolRepository;
import com.tropimotos.repository.UsuarioRepository;
import com.tropimotos.security.JwtService;
import com.tropimotos.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@Service: Marca la clase como un componente de servicio de Spring
 *           Permite la inyeccion automatica de dependencias
 *@RequiredArgsConstructor: Lombok - genera constructor con todos los campos final
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /** Repositorio para acceder a datos de usuarios */
    private final UsuarioRepository usuarioRepository;
    
    /** Repositorio para acceder a datos de roles */
    private final RolRepository rolRepository;
    
    /** Codificador de contrasenas (BCrypt) */
    private final PasswordEncoder passwordEncoder;
    
    /** Servicio de generacion de tokens JWT */
    private final JwtService jwtService;

    /**
     * Autentica un usuario con email y contrasena
     * Valida las credenciales y genera un token JWT si son correctas
     * 
     * @param request Credenciales del usuario (email y password)
     * @return AuthResponse con el token JWT y datos del usuario
     * @throws RuntimeException si las credenciales son invalidas
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        // Busca el usuario por email en la base de datos
        // orElseThrow lanza una excepcion si no se encuentra
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        // Verifica que la contrasena coincida usando BCrypt
        // passwordEncoder.matches compara la contrasena proporcionada con la encriptada
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Genera un token JWT para el usuario autenticado
        String token = jwtService.generateToken(usuario);

        // Retorna la respuesta de autenticacion con todos los datos necesarios
        return new AuthResponse(
                token,
                usuario.getIdUsuario(),
                usuario.getNombre() + " " + usuario.getApellido(),
                usuario.getEmail(),
                usuario.getRol().getNombre()
        );
    }

    /**
     * Registra un nuevo usuario en el sistema
     * Asigna automaticamente el rol CLIENTE y encripta la contrasena
     * 
     * @param request Datos del nuevo usuario
     * @return UsuarioDTO con los datos del usuario creado
     * @throws RuntimeException si el email ya existe o el rol CLIENTE no se encuentra
     */
    @Override
    @Transactional  // Asegura que la operacion sea atomica (rollback en caso de error)
    public UsuarioDTO registro(RegistroRequest request) {
        // Verifica si el email ya esta registrado
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Busca el rol CLIENTE para asignarlo al nuevo usuario
        // orElseThrow lanza excepcion si el rol no existe en la BD
        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

        // Crea una nueva instancia de Usuario y establece sus propiedades
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setTelefono(request.getTelefono());
        usuario.setEmail(request.getEmail());
        // Encripta la contrasena antes de guardarla (nunca se guarda en texto plano)
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rolCliente);
        usuario.setEstadoCuenta("ACTIVO");

        // Guarda el usuario en la base de datos
        usuario = usuarioRepository.save(usuario);

        // Convierte la entidad a DTO para retornarla
        return convertirADTO(usuario);
    }

    /**
     * Convierte una entidad Usuario a un UsuarioDTO
     * Este metodo se usa para no exponer datos sensibles como la contrasena
     * 
     * @param usuario Entidad Usuario a convertir
     * @return UsuarioDTO con los datos publicos del usuario
     */
    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setIdRol(usuario.getRol().getIdRol());
        dto.setNombreRol(usuario.getRol().getNombre());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setTelefono(usuario.getTelefono());
        dto.setEmail(usuario.getEmail());
        dto.setFotoPerfil(usuario.getFotoPerfil());
        dto.setEstadoCuenta(usuario.getEstadoCuenta());
        return dto;
    }
}
