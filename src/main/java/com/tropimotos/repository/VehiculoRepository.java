/**
 * Repository VehiculoRepository - Acceso a datos de la entidad Vehiculo
 * 
 * Proporciona metodos para interactuar con la tabla 'vehiculos' en la base de datos.
 */
package com.tropimotos.repository;

import com.tropimotos.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 *@Repository: Marca esta interfaz como un componente de repositorio de Spring
 *JpaRepository<Vehiculo, Integer>: Proporciona operaciones CRUD genericas
 *- Vehiculo: Tipo de entidad que maneja este repositorio
 *- Integer: Tipo de dato de la clave primaria (id_vehiculo)
 */
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    
    /**
     * Busca todos los vehiculos asociados a un chofer especifico
     * Un chofer puede tener uno o varios vehiculos registrados
     * 
     * @param idChofer ID del chofer cuyos vehiculos se buscan
     * @return Lista de vehiculos del chofer
     */
    List<Vehiculo> findByChoferIdChofer(Integer idChofer);
    
    /**
     * Busca un vehiculo por su numero de placa
     * La placa es unica por vehiculo
     * 
     * @param placa Numero de placa del vehiculo a buscar
     * @return Optional con el vehiculo si existe, vacio si no se encuentra
     */
    Optional<Vehiculo> findByPlaca(String placa);
    
    /**
     * Verifica si existe un vehiculo con la placa especificada
     * Util para validar antes de registrar un nuevo vehiculo
     * 
     * @param placa Numero de placa a verificar
     * @return true si existe un vehiculo con esa placa, false en caso contrario
     */
    boolean existsByPlaca(String placa);
}
