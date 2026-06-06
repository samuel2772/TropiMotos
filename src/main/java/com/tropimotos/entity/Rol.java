/**
 * Entidad Rol - Representa los roles de usuario en el sistema
 * 
 * Mapea la tabla 'roles' de la base de datos PostgreSQL.
 * Los roles definen los permisos y accesos de cada usuario (ej: CLIENTE, CHOFER, ADMIN).
 */
package com.tropimotos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    /**
     * Identificador unico del rol (Primary Key)
     * 
     * @Id: Marca este campo como clave primaria
     * @GeneratedValue: La base de datos genera automaticamente el valor (autoincrement)
     * @Column: Mapea el nombre de la columna en la tabla
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    /**
     * Nombre del rol (ej: "CLIENTE", "CHOFER", "ADMIN")
     * No puede ser nulo, maximo 50 caracteres
     */
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    /**
     * Descripcion detallada del rol
     * Opcional, maximo 255 caracteres
     */
    @Column(name = "descripcion", length = 255)
    private String descripcion;
}
