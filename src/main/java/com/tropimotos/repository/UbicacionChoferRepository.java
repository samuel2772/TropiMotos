/**
 * Repository UbicacionChoferRepository - Acceso a datos de la entidad UbicacionChofer
 * 
 * Proporciona metodos para interactuar con la tabla 'ubicaciones_chofer' en la base de datos.
 * Permite gestionar el tracking GPS de los choferes.
 */
package com.tropimotos.repository;

import com.tropimotos.entity.UbicacionChofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

/**
 *@Repository: Marca esta interfaz como un componente de repositorio de Spring
 *JpaRepository<UbicacionChofer, Integer>: Proporciona operaciones CRUD genericas
 *- UbicacionChofer: Tipo de entidad que maneja este repositorio
 *- Integer: Tipo de dato de la clave primaria (id_ubicacion)
 */
@Repository
public interface UbicacionChoferRepository extends JpaRepository<UbicacionChofer, Integer> {
    
    /**
     * Obtiene la ubicacion mas reciente de un chofer especifico
     * Ordena por fecha_hora de forma descendente y toma la primera
     * 
     * @param idChofer ID del chofer cuya ubicacion se busca
     * @return Optional con la ultima ubicacion del chofer, vacio si no hay registros
     */
    Optional<UbicacionChofer> findTopByChoferIdChoferOrderByFechaHoraDesc(Integer idChofer);
    
    /**
     * Busca todas las ubicaciones registradas de un chofer especifico
     * 
     * @param idChofer ID del chofer cuyas ubicaciones se buscan
     * @return Lista de todas las ubicaciones del chofer
     */
    List<UbicacionChofer> findByChoferIdChofer(Integer idChofer);
}
