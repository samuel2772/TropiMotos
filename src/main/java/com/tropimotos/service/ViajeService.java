/**
 * Interfaz ViajeService - Define los metodos para gestion de viajes
 * 
 * Esta interfaz establece el contrato para las operaciones sobre
 * la entidad Viaje, incluyendo su ciclo de vida completo.
 */
package com.tropimotos.service;

import com.tropimotos.dto.ViajeDTO;
import com.tropimotos.dto.ViajeRequest;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los metodos de gestion de viajes
 * La implementacion se encuentra en ViajeServiceImpl
 */
public interface ViajeService {
    
    /**
     * Lista todos los viajes del sistema
     * 
     * @return Lista de ViajeDTO con todos los viajes
     */
    List<ViajeDTO> listarTodos();
    
    /**
     * Busca un viaje por su ID
     * 
     * @param id ID del viaje a buscar
     * @return Optional con el viaje si existe, vacio si no
     */
    Optional<ViajeDTO> buscarPorId(Integer id);
    
    /**
     * Lista todos los viajes de un usuario especifico
     * 
     * @param idUsuario ID del usuario cuyos viajes se buscan
     * @return Lista de viajes del usuario
     */
    List<ViajeDTO> buscarPorUsuarioId(Integer idUsuario);
    
    /**
     * Lista todos los viajes de un chofer especifico
     * 
     * @param idChofer ID del chofer cuyos viajes se buscan
     * @return Lista de viajes del chofer
     */
    List<ViajeDTO> buscarPorChoferId(Integer idChofer);
    
    /**
     * Solicita un nuevo viaje
     * Crea el viaje en estado SOLICITADO
     * 
     * @param idUsuario ID del usuario que solicita el viaje
     * @param request Objeto con los datos del viaje (origen, destino)
     * @return ViajeDTO con los datos del viaje creado
     * @throws RuntimeException si el usuario no existe
     */
    ViajeDTO crear(Integer idUsuario, ViajeRequest request);
    
    /**
     * Un chofer acepta un viaje solicitado
     * Cambia el estado a ACEPTADO y asigna chofer y vehiculo
     * 
     * @param idViaje ID del viaje a aceptar
     * @param idChofer ID del chofer que acepta
     * @param idVehiculo ID del vehiculo a usar
     * @return ViajeDTO con los datos actualizados
     * @throws RuntimeException si viaje, chofer o vehiculo no existen
     */
    ViajeDTO aceptarViaje(Integer idViaje, Integer idChofer, Integer idVehiculo);
    
    /**
     * Un chofer inicia el viaje con el pasajero
     * Cambia el estado a EN_CURSO
     * 
     * @param idViaje ID del viaje a iniciar
     * @return ViajeDTO con los datos actualizados
     * @throws RuntimeException si el viaje no existe
     */
    ViajeDTO iniciarViaje(Integer idViaje);
    
    /**
     * Finaliza un viaje completado
     * Cambia el estado a COMPLETADO
     * 
     * @param idViaje ID del viaje a finalizar
     * @return ViajeDTO con los datos actualizados
     * @throws RuntimeException si el viaje no existe
     */
    ViajeDTO finalizarViaje(Integer idViaje);
    
    /**
     * Cancela un viaje
     * Cambia el estado a CANCELADO y registra quien lo cancelo
     * 
     * @param idViaje ID del viaje a cancelar
     * @param canceladoPor Quien cancela (USUARIO, CHOFER, SISTEMA)
     * @param observacion Motivo de cancelacion
     * @return ViajeDTO con los datos actualizados
     * @throws RuntimeException si el viaje no existe
     */
    ViajeDTO cancelarViaje(Integer idViaje, String canceladoPor, String observacion);
}
