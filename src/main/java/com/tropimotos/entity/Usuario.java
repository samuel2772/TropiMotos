/**
 * Entidad Usuario - Representa los usuarios del sistema TropiMotos
 * 
 * Incluye clientes y cualquier persona que use la aplicacion.
 * Mapea la tabla 'usuarios' de la base de datos PostgreSQL.
 */
package com.tropimotos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    /**
     * Identificador unico del usuario (Primary Key)
     * Se genera automaticamente con estrategia IDENTITY (autoincrement)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /**
     * Relacion ManyToOne con la entidad Rol
     * Un usuario tiene un solo rol (ej: CLIENTE o CHOFER)
     * 
     * FetchType.LAZY: Carga diferida - solo trae el rol cuando se accede
     * JoinColumn: Especifica la columna de llave foranea
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    /**
     * Nombre del usuario
     * Campo obligatorio (nullable = false)
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Apellido del usuario
     * Campo obligatorio
     */
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    /**
     * Numero de telefono del usuario
     * Campo obligatorio, maximo 20 caracteres
     */
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    /**
     * Correo electronico del usuario
     * Campo unico (no puede haber dos usuarios con el mismo email)
     */
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    /**
     * Contrasena del usuario (encriptada con BCrypt)
     * Campo obligatorio
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * URL de la foto de perfil del usuario
     * Campo opcional
     */
    @Column(name = "foto_perfil")
    private String fotoPerfil;

    /**
     * Estado de la cuenta del usuario
     * Valores posibles: ACTIVO, INACTIVO, SUSPENDIDO
     */
    @Column(name = "estado_cuenta", length = 20)
    private String estadoCuenta;

    /**
     * Fecha y hora de registro del usuario
     * Se asigna automaticamente al crear el usuario
     */
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    /**
     * Fecha y hora de la ultima actualizacion del perfil
     * Se actualiza automaticamente en cada modificacion
     */
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    /**
     * Callback que se ejecuta ANTES de persistir un nuevo usuario
     * Asigna automaticamente las fechas y el estado por defecto
     */
    @PrePersist
    protected void onCreate() {
        // Asigna la fecha y hora actual al momento del registro
        fechaRegistro = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        // Si no se especifica estado, por defecto es ACTIVO
        if (estadoCuenta == null) {
            estadoCuenta = "ACTIVO";
        }
    }

    /**
     * Callback que se ejecuta ANTES de actualizar un usuario existente
     * Actualiza automaticamente la fecha de modificacion
     */
    @PreUpdate
    protected void onUpdate() {
        // Actualiza la fecha cada vez que se modifica el registro
        fechaActualizacion = LocalDateTime.now();
    }
}
