/**
 * Repository UsuarioRepository - Acceso a datos de la entidad Usuario
 * 
 * Proporciona metodos para interactuar con la tabla 'usuarios' en la base de datos.
 */
package com.tropimotos.repository;

import com.tropimotos.entity.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 *@Repository: Marca esta interfaz como un componente de repositorio de Spring
 *JpaRepository<Usuario, Integer>: Proporciona operaciones CRUD genericas
 *- Usuario: Tipo de entidad que maneja este repositorio
 *- Integer: Tipo de dato de la clave primaria (id_usuario)
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Busca un usuario por su email
     * Util para autenticacion y verificacion de registro
     * 
     * @param email Email del usuario a buscar
     * @return Optional con el usuario si existe, vacio si no se encuentra
     */
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.email = :email")
    Optional<Usuario> findByEmailWithRol(@Param("email") String email);
    
    /**
     * Busca un usuario por su numero de telefono
     * Util para busquedas o verificacion de telefono
     * 
     * @param telefono Numero de telefono del usuario a buscar
     * @return Optional con el usuario si existe, vacio si no se encuentra
     */
    Optional<Usuario> findByTelefono(String telefono);
    
    /**
     * Verifica si existe un usuario con el email especificado
     * Util para validar antes de crear un nuevo usuario
     * 
     * @param email Email a verificar
     * @return true si existe un usuario con ese email, false en caso contrario
     */
    boolean existsByEmail(String email);
}
