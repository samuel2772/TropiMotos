/**
 * Interfaz UsuarioService - Define los metodos para gestion de usuarios
 * 
 * Esta interfaz establece el contrato para las operaciones CRUD
 * sobre la entidad Usuario.
 */
package com.tropimotos.service;

import com.tropimotos.dto.UsuarioDTO;
import com.tropimotos.entity.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los metodos de gestion de usuarios
 * La implementacion se encuentra en UsuarioServiceImpl
 */
public interface UsuarioService {
    
    /**
     * Lista todos los usuarios del sistema
     * 
     * @return Lista de UsuarioDTO con todos los usuarios
     */
    List<UsuarioDTO> listarTodos();
    
    /**
     * Busca un usuario por su ID
     * 
     * @param id ID del usuario a buscar
     * @return Optional con el usuario si existe, vacio si no
     */
    Optional<UsuarioDTO> buscarPorId(Integer id);
    
    /**
     * Busca un usuario por su email
     * 
     * @param email Email del usuario a buscar
     * @return Optional con el usuario si existe, vacio si no
     */
    Optional<Usuario> buscarPorEmail(String email);
    
    /**
     * Crea un nuevo usuario en el sistema
     * 
     * @param usuario Entidad Usuario con los datos a crear
     * @return UsuarioDTO con los datos del usuario creado
     */
    UsuarioDTO crear(Usuario usuario);
    
    /**
     * Actualiza un usuario existente
     * 
     * @param id ID del usuario a actualizar
     * @param usuario Entidad Usuario con los nuevos datos
     * @return UsuarioDTO con los datos actualizados
     * @throws RuntimeException si el usuario no existe
     */
    UsuarioDTO actualizar(Integer id, Usuario usuario);
    
    /**
     * Elimina un usuario del sistema
     * 
     * @param id ID del usuario a eliminar
     */
    void eliminar(Integer id);
}
