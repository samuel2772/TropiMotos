/**
 * DTO UbicacionChoferRequest - Objeto para recibir datos de ubicacion GPS
 * 
 * Este DTO se utiliza para recibir las coordenadas GPS
 * cuando un chofer actualiza su ubicacion.
 */
package com.tropimotos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class UbicacionChoferRequest {
    
    /**
     * ID del chofer cuya ubicacion se esta actualizando
     * Campo obligatorio
     */
    @NotNull(message = "El ID del chofer es obligatorio")
    private Integer idChofer;

    /**
     * Latitud GPS actual del chofer
     * Campo obligatorio
     */
    @NotNull(message = "La latitud es obligatoria")
    private Double latitud;

    /**
     * Longitud GPS actual del chofer
     * Campo obligatorio
     */
    @NotNull(message = "La longitud es obligatoria")
    private Double longitud;

    /**
     * Precision del GPS en metros
     * Campo opcional
     */
    private Double precisionGps;
}
