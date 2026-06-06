/**
 * Entidad Viaje - Representa un viaje solicitado por un usuario
 * 
 * Contiene toda la informacion del viaje incluyendo origen, destino,
 * datos del chofer asignado, vehiculo y tarifario.
 * Mapea la tabla 'viajes' de la base de datos PostgreSQL.
 */
package com.tropimotos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "viajes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {

    /**
     * Identificador unico del viaje (Primary Key)
     * Se genera automaticamente
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viaje")
    private Integer idViaje;

    /**
     * Relacion ManyToOne con la entidad Usuario (cliente que solicita el viaje)
     * Obligatorio - todo viaje debe tener un usuario solicitante
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Relacion ManyToOne con la entidad Chofer
     * Opcional - se asigna cuando un chofer acepta el viaje
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chofer")
    private Chofer chofer;

    /**
     * Relacion ManyToOne con la entidad Vehiculo
     * Opcional - se asigna cuando un chofer acepta el viaje
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    /**
     * Relacion ManyToOne con la entidad EstadoViaje
     * Obligatorio - todo viaje tiene un estado actual
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoViaje estadoViaje;

    // ========== DATOS DEL ORIGEN ==========
    
    /**
     * Texto descriptivo del origen (direccion)
     */
    @Column(name = "origen_texto", length = 255)
    private String origenTexto;

    /**
     * Latitud GPS del punto de origen
     */
    @Column(name = "origen_latitud")
    private BigDecimal origenLatitud;

    /**
     * Longitud GPS del punto de origen
     */
    @Column(name = "origen_longitud")
    private BigDecimal origenLongitud;

    // ========== DATOS DEL DESTINO ==========
    
    /**
     * Texto descriptivo del destino (direccion)
     */
    @Column(name = "destino_texto", length = 255)
    private String destinoTexto;

    /**
     * Latitud GPS del punto de destino
     */
    @Column(name = "destino_latitud")
    private BigDecimal destinoLatitud;

    /**
     * Longitud GPS del punto de destino
     */
    @Column(name = "destino_longitud")
    private BigDecimal destinoLongitud;

    // ========== DATOS DEL VIAJE ==========
    
    /**
     * Distancia estimada o real del viaje en kilometros
     */
    @Column(name = "distancia_km")
    private BigDecimal distanciaKm;

    /**
     * Tiempo estimado del viaje en minutos
     */
    @Column(name = "tiempo_estimado_min")
    private Integer tiempoEstimadoMin;

    /**
     * Tarifa calculada para el viaje
     * Se calcula basandose en distancia y tiempo
     */
    @Column(name = "tarifa_calculada")
    private BigDecimal tarifaCalculada;

    // ========== FECHAS DEL VIAJE ==========
    
    /**
     * Fecha y hora en que el usuario solicito el viaje
     */
    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;

    /**
     * Fecha y hora en que un chofer acepto el viaje
     */
    @Column(name = "fecha_aceptacion")
    private LocalDateTime fechaAceptacion;

    /**
     * Fecha y hora en que el viaje comenzo (chofer inicio el recorrido)
     */
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    /**
     * Fecha y hora en que el viaje finalizo o fue cancelado
     */
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    // ========== DATOS DE CANCELACION ==========
    
    /**
     * Indica quien cancelo el viaje
     * Valores: USUARIO, CHOFER, SISTEMA
     */
    @Column(name = "cancelado_por", length = 50)
    private String canceladoPor;

    /**
     * Observacion o motivo de cancelacion del viaje
     */
    @Column(name = "observacion", length = 500)
    private String observacion;

    /**
     * Callback que se ejecuta ANTES de crear un nuevo viaje
     * Asigna automaticamente la fecha de solicitud
     */
    @PrePersist
    protected void onCreate() {
        // Asigna la fecha y hora actual al solicitar el viaje
        fechaSolicitud = LocalDateTime.now();
    }
}
