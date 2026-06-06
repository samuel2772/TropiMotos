/**
 * Repository RolRepository - Acceso a datos de la entidad Rol
 * 
 * Proporciona metodos para interactuar con la tabla 'roles' en la base de datos.
 * JpaRepository ya proporciona operaciones CRUD basicas (save, findById, delete, etc.).
 */
package com.tropimotos.repository;

import com.tropimotos.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 *@Repository: Marca esta interfaz como un componente de repositorio de Spring
 *              Permite la deteccion automatica de beans y el manejo de excepciones de persistencia
 * 
 * JpaRepository<Rol, Integer>: Proporciona operaciones CRUD genericas
 * - Rol: Tipo de entidad que maneja este repositorio
 * - Integer: Tipo de dato de la clave primaria (id_rol)
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    /**
     * Busca un rol por su nombre
     * Spring Data JPA genera automaticamente la consulta SQL
     * 
     * @param nombre Nombre del rol a buscar
     * @return Optional con el rol si existe, vacio si no se encuentra
     */
    Optional<Rol> findByNombre(String nombre);
}
