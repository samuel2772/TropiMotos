/**
 * Repository ViajeRepository - Acceso a datos de la entidad Viaje
 * 
 * Proporciona metodos para interactuar con la tabla 'viajes' en la base de datos.
 */
package com.tropimotos.repository;

import com.tropimotos.entity.Viaje;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *@Repository: Marca esta interfaz como un componente de repositorio de Spring
 *JpaRepository<Viaje, Integer>: Proporciona operaciones CRUD genericas
 *- Viaje: Tipo de entidad que maneja este repositorio
 *- Integer: Tipo de dato de la clave primaria (id_viaje)
 */
@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Integer> {

    @Override
    @EntityGraph(attributePaths = {"usuario", "chofer", "chofer.usuario", "vehiculo", "estadoViaje"})
    List<Viaje> findAll();

    @Override
    @EntityGraph(attributePaths = {"usuario", "chofer", "chofer.usuario", "vehiculo", "estadoViaje"})
    Optional<Viaje> findById(Integer id);
    
    /**
     * Busca todos los viajes solicitados por un usuario especifico
     * 
     * @param idUsuario ID del usuario cuyos viajes se buscan
     * @return Lista de viajes del usuario
     */
    @EntityGraph(attributePaths = {"usuario", "chofer", "chofer.usuario", "vehiculo", "estadoViaje"})
    List<Viaje> findByUsuarioIdUsuario(Integer idUsuario);
    
    /**
     * Busca todos los viajes aceptados por un chofer especifico
     * 
     * @param idChofer ID del chofer cuyos viajes se buscan
     * @return Lista de viajes del chofer
     */
    @EntityGraph(attributePaths = {"usuario", "chofer", "chofer.usuario", "vehiculo", "estadoViaje"})
    List<Viaje> findByChoferIdChofer(Integer idChofer);
    
    /**
     * Busca todos los viajes con un estado especifico
     * 
     * @param idEstado ID del estado de viaje a filtrar
     * @return Lista de viajes con ese estado
     */
    @EntityGraph(attributePaths = {"usuario", "chofer", "chofer.usuario", "vehiculo", "estadoViaje"})
    List<Viaje> findByEstadoViajeIdEstado(Integer idEstado);
}
