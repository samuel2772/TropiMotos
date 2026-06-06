/**
 * Interfaz ChoferService - Define los metodos para gestion de chofers
 * 
 * Esta interfaz establece el contrato para las operaciones sobre
 * la entidad Chofer y su disponibilidad.
 */
package com.tropimotos.service;

import com.tropimotos.dto.ChoferDTO;
import com.tropimotos.dto.ChoferRequest;
import com.tropimotos.entity.Chofer;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los metodos de gestion de chofers
 * La implementacion se encuentra en ChoferServiceImpl
 */
public interface ChoferService {
    
    /**
     * Lista todos los chofers del sistema
     * 
     * @return Lista de ChoferDTO con todos los chofers
     */
    List<ChoferDTO> listarTodos();
    
    /**
     * Busca un chofer por su ID
     * 
     * @param id ID del chofer a buscar
     * @return Optional con el chofer si existe, vacio si no
     */
    Optional<ChoferDTO> buscarPorId(Integer id);
    
    /**
     * Busca un chofer asociado a un usuario especifico
     * 
     * @param idUsuario ID del usuario asociado al chofer
     * @return Optional con el chofer si existe, vacio si no
     */
    Optional<Chofer> buscarPorUsuarioId(Integer idUsuario);
    
    /**
     * Registra un nuevo chofer en el sistema
     * 
     * @param request Objeto con los datos del chofer
     * @return ChoferDTO con los datos del chofer creado
     * @throws RuntimeException si el usuario no existe
     */
    ChoferDTO crear(ChoferRequest request);
    
    /**
     * Actualiza los datos de un chofer existente
     * 
     * @param id ID del chofer a actualizar
     * @param request Objeto con los nuevos datos
     * @return ChoferDTO con los datos actualizados
     * @throws RuntimeException si el chofer no existe
     */
    ChoferDTO actualizar(Integer id, ChoferRequest request);
    
    /**
     * Actualiza la disponibilidad de un chofer
     * 
     * @param id ID del chofer a actualizar
     * @param disponible Nuevo estado de disponibilidad
     * @return ChoferDTO con los datos actualizados
     * @throws RuntimeException si el chofer no existe
     */
    ChoferDTO actualizarDisponibilidad(Integer id, Boolean disponible);
    
    /**
     * Elimina un chofer del sistema
     * 
     * @param id ID del chofer a eliminar
     */
    void eliminar(Integer id);
    
    /**
     * Lista todos los chofers verificados y disponibles
     * Util para mostrar a usuarios que buscan conductores
     * 
     * @return Lista de chofers disponibles y verificados
     */
    List<ChoferDTO> listarDisponibles();
}
