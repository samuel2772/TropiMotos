/**
 * Implementacion del servicio de gestion de usuarios
 * 
 * Esta clase implementa la interfaz UsuarioService y proporciona
 * la logica de negocio para las operaciones CRUD sobre usuarios.
 */
package com.tropimotos.service.impl;

import com.tropimotos.dto.UsuarioDTO;
import com.tropimotos.entity.Usuario;
import com.tropimotos.repository.UsuarioRepository;
import com.tropimotos.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *@Service: Marca la clase como un componente de servicio de Spring
 *@RequiredArgsConstructor: Lombok - genera constructor con todos los campos final
 */
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    /** Repositorio para acceder a datos de usuarios */
    private final UsuarioRepository usuarioRepository;

    /**
     * Lista todos los usuarios del sistema
     * Convierte cada entidad Usuario a UsuarioDTO
     * 
     * @return Lista de UsuarioDTO con todos los usuarios
     */
    @Override
    public List<UsuarioDTO> listarTodos() {
        // findAll() obtiene todos los usuarios de la BD
        // stream() permite procesar cada elemento
        // map() convierte cada Usuario a UsuarioDTO
        // collect() reune los resultados en una lista
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su ID
     * 
     * @param id ID del usuario a buscar
     * @return Optional con el usuario si existe, vacio si no
     */
    @Override
    public Optional<UsuarioDTO> buscarPorId(Integer id) {
        // findById busca por clave primaria
        // map aplica la conversion si el Optional tiene valor
        return usuarioRepository.findById(id)
                .map(this::convertirADTO);
    }

    /**
     * Busca un usuario por su email
     * 
     * @param email Email del usuario a buscar
     * @return Optional con el usuario si existe, vacio si no
     */
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Crea un nuevo usuario en el sistema
     * 
     * @param usuario Entidad Usuario con los datos a crear
     * @return UsuarioDTO con los datos del usuario creado
     */
    @Override
    @Transactional
    public UsuarioDTO crear(Usuario usuario) {
        // save() inserta el usuario en la BD y lo retorna con el ID generado
        usuario = usuarioRepository.save(usuario);
        return convertirADTO(usuario);
    }

    /**
     * Actualiza un usuario existente
     * Solo actualiza los campos permitidos (no el email ni password)
     * 
     * @param id ID del usuario a actualizar
     * @param usuarioActualizar Entidad con los nuevos datos
     * @return UsuarioDTO con los datos actualizados
     * @throws RuntimeException si el usuario no existe
     */
    @Override
    @Transactional
    public UsuarioDTO actualizar(Integer id, Usuario usuarioActualizar) {
        // Busca el usuario existente
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualiza solo los campos permitidos
        usuario.setNombre(usuarioActualizar.getNombre());
        usuario.setApellido(usuarioActualizar.getApellido());
        usuario.setTelefono(usuarioActualizar.getTelefono());
        usuario.setFotoPerfil(usuarioActualizar.getFotoPerfil());
        usuario.setEstadoCuenta(usuarioActualizar.getEstadoCuenta());

        // Guarda los cambios
        usuario = usuarioRepository.save(usuario);
        return convertirADTO(usuario);
    }

    /**
     * Elimina un usuario del sistema
     * 
     * @param id ID del usuario a eliminar
     */
    @Override
    @Transactional
    public void eliminar(Integer id) {
        // deleteById() elimina el registro por su clave primaria
        usuarioRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Usuario a un UsuarioDTO
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
