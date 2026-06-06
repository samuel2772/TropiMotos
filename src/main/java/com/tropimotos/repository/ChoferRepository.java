/**
 * Repository ChoferRepository - Acceso a datos de la entidad Chofer
 * 
 * Proporciona metodos para interactuar con la tabla 'choferes' en la base de datos.
 */
package com.tropimotos.repository;

import com.tropimotos.entity.Chofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

/**
 *@Repository: Marca esta interfaz como un componente de repositorio de Spring
 *JpaRepository<Chofer, Integer>: Proporciona operaciones CRUD genericas
 *- Chofer: Tipo de entidad que maneja este repositorio
 *- Integer: Tipo de dato de la clave primaria (id_chofer)
 */
@Repository
public interface ChoferRepository extends JpaRepository<Chofer, Integer> {
    
    /**
     * Busca un chofer asociado a un usuario especifico
     * Un usuario solo puede tener un registro de chofer
     * 
     * @param idUsuario ID del usuario asociado al chofer
     * @return Optional con el chofer si existe, vacio si no se encuentra
     */
    Optional<Chofer> findByUsuarioIdUsuario(Integer idUsuario);
    
    /**
     * Busca todos los choferes disponibles para接受了 viajes
     * Filtra solo los que tienen disponible = true
     * 
     * @return Lista de choferes disponibles
     */
    List<Chofer> findByDisponibleTrue();
    
    /**
     * Busca choferes verificados y disponibles
     * Solo retorna choferes que pueden efectivamente realizar viajes
     * 
     * @return Lista de choferes verificados y disponibles
     */
    List<Chofer> findByVerificadoTrueAndDisponibleTrue();
    
    /**
     * Verifica si existe un chofer con el numero de licencia especificado
     * 
     * @param numeroLicencia Numero de licencia a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByNumeroLicencia(String numeroLicencia);
    
    /**
     * Verifica si existe un chofer con la cedula de identidad especificada
     * 
     * @param ci Cedula de identidad a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByCi(String ci);
}
