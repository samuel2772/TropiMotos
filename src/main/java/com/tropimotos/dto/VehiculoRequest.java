/**
 * DTO VehiculoRequest - Objeto para recibir datos de vehiculo
 * 
 * Este DTO se utiliza para recibir los datos necesarios
 * cuando se registra o actualiza un vehiculo en el sistema.
 */
package com.tropimotos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class VehiculoRequest {
    
    /**
     * ID del chofer propietario del vehiculo
     */
    private Integer idChofer;

    /**
     * Tipo de vehiculo (ej: MOTO, CARRO, SUV)
     * Campo obligatorio
     */
    @NotBlank(message = "El tipo de vehículo es obligatorio")
    private String tipoVehiculo;

    /**
     * Marca del vehiculo
     * Campo obligatorio
     */
    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    /**
     * Modelo del vehiculo
     * Campo obligatorio
     */
    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    /**
     * Color del vehiculo
     * Campo obligatorio
     */
    @NotBlank(message = "El color es obligatorio")
    private String color;

    /**
     * Numero de placa del vehiculo
     * Campo obligatorio
     */
    @NotBlank(message = "La placa es obligatoria")
    private String placa;

    /**
     * Ano de fabricacion del vehiculo
     * Campo opcional
     */
    private Integer anio;
}
