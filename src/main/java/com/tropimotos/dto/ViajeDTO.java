/**
 * DTO ViajeDTO - Objeto de transferencia de datos de Viaje
 * 
 * Este DTO se utiliza para enviar informacion completa de un viaje
 * a traves de la API, incluyendo datos del usuario, chofer y vehiculo.
 */
package com.tropimotos.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViajeDTO {
    
    // ========== DATOS DEL VIAJE ==========
    /** Identificador unico del viaje */
    private Integer idViaje;
    
    /** ID del usuario que solicito el viaje */
    private Integer idUsuario;
    
    /** Nombre del usuario */
    private String nombreUsuario;
    
    /** ID del chofer asignado */
    private Integer idChofer;
    
    /** Nombre del chofer */
    private String nombreChofer;
    
    /** ID del vehiculo asignado */
    private Integer idVehiculo;
    
    /** Placa del vehiculo */
    private String placaVehiculo;
    
    /** ID del estado del viaje */
    private Integer idEstado;
    
    /** Nombre del estado del viaje */
    private String nombreEstado;
    
    // ========== DATOS DEL ORIGEN ==========
    /** Texto descriptivo del origen */
    private String origenTexto;
    
    /** Latitud del origen */
    private BigDecimal origenLatitud;
    
    /** Longitud del origen */
    private BigDecimal origenLongitud;
    
    // ========== DATOS DEL DESTINO ==========
    /** Texto descriptivo del destino */
    private String destinoTexto;
    
    /** Latitud del destino */
    private BigDecimal destinoLatitud;
    
    /** Longitud del destino */
    private BigDecimal destinoLongitud;
    
    // ========== DATOS DEL VIAJE ==========
    /** Distancia del viaje en kilometros */
    private BigDecimal distanciaKm;
    
    /** Tiempo estimado en minutos */
    private Integer tiempoEstimadoMin;
    
    /** Tarifa calculada */
    private BigDecimal tarifaCalculada;
    
    // ========== FECHAS ==========
    /** Fecha de solicitud del viaje */
    private LocalDateTime fechaSolicitud;
    
    /** Fecha de aceptacion del viaje */
    private LocalDateTime fechaAceptacion;
    
    /** Fecha de inicio del viaje */
    private LocalDateTime fechaInicio;
    
    /** Fecha de fin del viaje */
    private LocalDateTime fechaFin;
    
    // ========== DATOS DE CANCELACION ==========
    /** Quien cancelo el viaje (USUARIO, CHOFER, SISTEMA) */
    private String canceladoPor;
    
    /** Observacion o motivo */
    private String observacion;
}
