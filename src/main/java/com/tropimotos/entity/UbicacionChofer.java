/**
 * Entidad UbicacionChofer - Almacena la ubicacion GPS de los choferes
 * 
 * Permite hacer seguimiento en tiempo real de la posicion de cada chofer.
 * Mapea la tabla 'ubicaciones_chofer' de la base de datos PostgreSQL.
 */
package com.tropimotos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ubicaciones_chofer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionChofer {

    /**
     * Identificador unico de la ubicacion (Primary Key)
     * Se genera automaticamente
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Integer idUbicacion;

    /**
     * Relacion ManyToOne con la entidad Chofer
     * Indica a que chofer pertenece esta ubicacion
     * Obligatorio
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chofer", nullable = false)
    private Chofer chofer;

    /**
     * Latitud GPS de la ubicacion actual del chofer
     * Campo obligatorio
     */
    @Column(name = "latitud", nullable = false)
    private Double latitud;

    /**
     * Longitud GPS de la ubicacion actual del chofer
     * Campo obligatorio
     */
    @Column(name = "longitud", nullable = false)
    private Double longitud;

    /**
     * Precision del GPS en metros
     * Indica que tan exacta es la medicion de ubicacion
     * Campo opcional
     */
    @Column(name = "precision_gps")
    private Double precisionGps;

    /**
     * Fecha y hora en que se registro esta ubicacion
     */
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    /**
     * Callback que se ejecuta ANTES de guardar una nueva ubicacion
     * Asigna automaticamente la fecha y hora actual
     */
    @PrePersist
    protected void onCreate() {
        // Asigna la fecha y hora actual al registrar la ubicacion
        fechaHora = LocalDateTime.now();
    }
}
