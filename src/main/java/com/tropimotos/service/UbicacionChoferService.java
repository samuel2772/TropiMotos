/**
 * Interfaz UbicacionChoferService - Define los metodos para gestion de ubicaciones GPS
 * 
 * Esta interfaz establece el contrato para las operaciones de
 * tracking GPS de los choferes.
 */
package com.tropimotos.service;

import com.tropimotos.dto.UbicacionChoferDTO;
import com.tropimotos.dto.UbicacionChoferRequest;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los metodos de gestion de ubicaciones
 * La implementacion se encuentra en UbicacionChoferServiceImpl
 */
public interface UbicacionChoferService {
    
    /**
     * Lista todas las ubicaciones registradas en el sistema
     * 
     * @return Lista de UbicacionChoferDTO con todas las ubicaciones
     */
    List<UbicacionChoferDTO> listarTodos();
    
    /**
     * Busca una ubicacion por su ID
     * 
     * @param id ID de la ubicacion a buscar
     * @return Optional con la ubicacion si existe, vacio si no
     */
    Optional<UbicacionChoferDTO> buscarPorId(Integer id);
    
    /**
     * Lista todas las ubicaciones de un chofer especifico
     * 
     * @param idChofer ID del chofer cuyas ubicaciones se buscan
     * @return Lista de ubicaciones del chofer
     */
    List<UbicacionChoferDTO> buscarPorChoferId(Integer idChofer);
    
    /**
     * Obtiene la ubicacion mas reciente de un chofer
     * Util para mostrar la posicion actual del chofer
     * 
     * @param idChofer ID del chofer
     * @return Optional con la ultima ubicacion del chofer
     */
    Optional<UbicacionChoferDTO> obtenerUltimaUbicacion(Integer idChofer);
    
    /**
     * Registra una nueva ubicacion para un chofer
     * 
     * @param request Objeto con los datos de ubicacion
     * @return UbicacionChoferDTO con los datos de la ubicacion creada
     * @throws RuntimeException si el chofer no existe
     */
    UbicacionChoferDTO crear(UbicacionChoferRequest request);
    
    /**
     * Actualiza la ubicacion de un chofer
     * Crea un nuevo registro de ubicacion
     * 
     * @param idChofer ID del chofer cuya ubicacion se actualiza
     * @param request Objeto con los nuevos datos de ubicacion
     * @return UbicacionChoferDTO con los datos actualizados
     * @throws RuntimeException si el chofer no existe
     */
    UbicacionChoferDTO actualizarUbicacion(Integer idChofer, UbicacionChoferRequest request);
}
