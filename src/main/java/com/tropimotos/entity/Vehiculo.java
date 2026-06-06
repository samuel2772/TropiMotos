/**
 * Entidad Vehiculo - Representa los vehiculos registrados en el sistema
 * 
 * Cada vehiculo pertenece a un chofer y se utiliza para realizar viajes.
 * Mapea la tabla 'vehiculos' de la base de datos PostgreSQL.
 */
package com.tropimotos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehiculos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    /**
     * Identificador unico del vehiculo (Primary Key)
     * Se genera automaticamente con autoincrement
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Integer idVehiculo;

    /**
     * Relacion ManyToOne con la entidad Chofer
     * Un vehiculo pertenece a un solo chofer
     * 
     * FetchType.LAZY: Carga diferida del chofer
     * nullable = false: Todo vehiculo debe tener un chofer asociado
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chofer", nullable = false)
    private Chofer chofer;

    /**
     * Tipo de vehiculo (ej: "MOTO", "CARRO", "SUV")
     * Campo obligatorio
     */
    @Column(name = "tipo_vehiculo", nullable = false, length = 50)
    private String tipoVehiculo;

    /**
     * Marca del vehiculo (ej: "Toyota", "Honda", "Yamaha")
     * Campo obligatorio
     */
    @Column(name = "marca", nullable = false, length = 50)
    private String marca;

    /**
     * Modelo del vehiculo (ej: "Corolla", "Civic")
     * Campo obligatorio
     */
    @Column(name = "modelo", nullable = false, length = 50)
    private String modelo;

    /**
     * Color del vehiculo (ej: "Rojo", "Azul", "Negro")
     * Campo obligatorio
     */
    @Column(name = "color", nullable = false, length = 30)
    private String color;

    /**
     * Numero de placa del vehiculo
     * Campo unico - no puede haber dos vehiculos con la misma placa
     * Obligatorio
     */
    @Column(name = "placa", nullable = false, unique = true, length = 20)
    private String placa;

    /**
     * Ano de fabricacion del vehiculo
     * Campo opcional
     */
    @Column(name = "anio")
    private Integer anio;

    /**
     * Estado del vehiculo
     * Valores: ACTIVO, INACTIVO, MANTENIMIENTO
     */
    @Column(name = "estado_vehiculo", length = 20)
    private String estadoVehiculo;

    /**
     * Fecha y hora de registro del vehiculo en el sistema
     */
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    /**
     * Callback que se ejecuta ANTES de crear un nuevo vehiculo
     * Asigna valores por defecto
     */
    @PrePersist
    protected void onCreate() {
        // Asigna la fecha y hora actual al registrar
        fechaRegistro = LocalDateTime.now();
        // Si no se especifica estado, por defecto es ACTIVO
        if (estadoVehiculo == null) estadoVehiculo = "ACTIVO";
    }
}
