/**
 * Interfaz VehiculoService - Define los metodos para gestion de vehiculos
 * 
 * Esta interfaz establece el contrato para las operaciones CRUD
 * sobre la entidad Vehiculo.
 */
package com.tropimotos.service;

import com.tropimotos.dto.VehiculoDTO;
import com.tropimotos.dto.VehiculoRequest;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los metodos de gestion de vehiculos
 * La implementacion se encuentra en VehiculoServiceImpl
 */
public interface VehiculoService {
    
    /**
     * Lista todos los vehiculos del sistema
     * 
     * @return Lista de VehiculoDTO con todos los vehiculos
     */
    List<VehiculoDTO> listarTodos();
    
    /**
     * Busca un vehiculo por su ID
     * 
     * @param id ID del vehiculo a buscar
     * @return Optional con el vehiculo si existe, vacio si no
     */
    Optional<VehiculoDTO> buscarPorId(Integer id);
    
    /**
     * Lista todos los vehiculos de un chofer especifico
     * 
     * @param idChofer ID del chofer cuyos vehiculos se buscan
     * @return Lista de vehiculos del chofer
     */
    List<VehiculoDTO> buscarPorChoferId(Integer idChofer);
    
    /**
     * Registra un nuevo vehiculo en el sistema
     * 
     * @param request Objeto con los datos del vehiculo
     * @return VehiculoDTO con los datos del vehiculo creado
     * @throws RuntimeException si el chofer no existe
     */
    VehiculoDTO crear(VehiculoRequest request);
    
    /**
     * Actualiza los datos de un vehiculo existente
     * 
     * @param id ID del vehiculo a actualizar
     * @param request Objeto con los nuevos datos
     * @return VehiculoDTO con los datos actualizados
     * @throws RuntimeException si el vehiculo no existe
     */
    VehiculoDTO actualizar(Integer id, VehiculoRequest request);
    
    /**
     * Elimina un vehiculo del sistema
     * 
     * @param id ID del vehiculo a eliminar
     */
    void eliminar(Integer id);
}
