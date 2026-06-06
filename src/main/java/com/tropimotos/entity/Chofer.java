/**
 * Entidad Chofer - Representa a los conductores del sistema TropiMotos
 * 
 * Un chofer es un usuario con rol de conductor que puede realizar viajes.
 * Mapea la tabla 'choferes' de la base de datos PostgreSQL.
 */
package com.tropimotos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "choferes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chofer {

    /**
     * Identificador unico del chofer (Primary Key)
     * Se genera automaticamente con autoincrement
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chofer")
    private Integer idChofer;

    /**
     * Relacion ManyToOne con la entidad Usuario
     * Un chofer esta asociado a un unico usuario
     * 
     * FetchType.LAZY: Carga la informacion del usuario solo cuando se necesita
     * nullable = false: Todo chofer debe tener un usuario asociado
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Numero de licencia de conducir del chofer
     * Campo obligatorio para poder operar
     */
    @Column(name = "numero_licencia", nullable = false, length = 50)
    private String numeroLicencia;

    /**
     * Cedula de identidad del chofer
     * Campo obligatorio para verificacion
     */
    @Column(name = "ci", nullable = false, length = 20)
    private String ci;

    /**
     * Indica si el chofer esta disponible para接受了 nuevos viajes
     * true = disponible, false = ocupado o fuera de servicio
     */
    @Column(name = "disponible")
    private Boolean disponible;

    /**
     * Indica si el chofer ha sido verificado por el sistema
     * true = verificado, false = pendiente de verificacion
     */
    @Column(name = "verificado")
    private Boolean verificado;

    /**
     * Estado actual del chofer
     * Valores: ACTIVO, INACTIVO, SUSPENDIDO
     */
    @Column(name = "estado_chofer", length = 20)
    private String estadoChofer;

    /**
     * Fecha y hora de registro del chofer en el sistema
     */
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    /**
     * Fecha y hora de la ultima actualizacion de datos del chofer
     */
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    /**
     * Callback que se ejecuta ANTES de crear un nuevo chofer
     * Inicializa valores por defecto
     */
    @PrePersist
    protected void onCreate() {
        // Asigna fecha y hora actual al registrar
        fechaRegistro = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        
        // Valores por defecto si no se especifican
        if (disponible == null) disponible = false;
        if (verificado == null) verificado = false;
        if (estadoChofer == null) estadoChofer = "ACTIVO";
    }

    /**
     * Callback que se ejecuta ANTES de actualizar datos del chofer
     * Actualiza la fecha de modificacion
     */
    @PreUpdate
    protected void onUpdate() {
        // Actualiza la fecha cada vez que se modifica el registro
        fechaActualizacion = LocalDateTime.now();
    }
}
