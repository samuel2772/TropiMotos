/**
 * DTO ViajeRequest - Objeto para recibir datos de solicitud de viaje
 * 
 * Este DTO se utiliza para recibir los datos necesarios
 * cuando un usuario solicita un nuevo viaje.
 */
package com.tropimotos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Data
public class ViajeRequest {
    
    /**
     * Latitud del punto de origen
     * Campo obligatorio
     */
    @NotNull(message = "El origen latitud es obligatorio")
    private BigDecimal origenLatitud;

    /**
     * Longitud del punto de origen
     * Campo obligatorio
     */
    @NotNull(message = "El origen longitud es obligatorio")
    private BigDecimal origenLongitud;

    /**
     * Texto descriptivo del origen (direccion)
     * Campo opcional
     */
    private String origenTexto;

    /**
     * Latitud del punto de destino
     * Campo obligatorio
     */
    @NotNull(message = "El destino latitud es obligatorio")
    private BigDecimal destinoLatitud;

    /**
     * Longitud del punto de destino
     * Campo obligatorio
     */
    @NotNull(message = "El destino longitud es obligatorio")
    private BigDecimal destinoLongitud;

    /**
     * Texto descriptivo del destino (direccion)
     * Campo opcional
     */
    private String destinoTexto;
}
