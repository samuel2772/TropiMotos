/**
 * Repository EstadoViajeRepository - Acceso a datos de la entidad EstadoViaje
 * 
 * Proporciona metodos para interactuar con la tabla 'estados_viaje' en la base de datos.
 */
package com.tropimotos.repository;

import com.tropimotos.entity.EstadoViaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 *@Repository: Marca esta interfaz como un componente de repositorio de Spring
 *JpaRepository<EstadoViaje, Integer>: Proporciona operaciones CRUD genericas
 *- EstadoViaje: Tipo de entidad que maneja este repositorio
 *- Integer: Tipo de dato de la clave primaria (id_estado)
 */
@Repository
public interface EstadoViajeRepository extends JpaRepository<EstadoViaje, Integer> {
    
    /**
     * Busca un estado de viaje por su nombre
     * Util para cambiar el estado de un viaje (ej: "SOLICITADO", "COMPLETADO")
     * 
     * @param nombreEstado Nombre del estado a buscar
     * @return Optional con el estado si existe, vacio si no se encuentra
     */
    Optional<EstadoViaje> findByNombreEstado(String nombreEstado);
}
