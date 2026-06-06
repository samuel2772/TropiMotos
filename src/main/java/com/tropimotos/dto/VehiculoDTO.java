/**
 * DTO VehiculoDTO - Objeto de transferencia de datos de Vehiculo
 * 
 * Este DTO se utiliza para enviar informacion de vehiculos
 * a traves de la API.
 */
package com.tropimotos.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {
    
    /** Identificador unico del vehiculo */
    private Integer idVehiculo;
    
    /** ID del chofer propietario */
    private Integer idChofer;
    
    /** Nombre completo del chofer */
    private String nombreChofer;
    
    /** Tipo de vehiculo (MOTO, CARRO, SUV) */
    private String tipoVehiculo;
    
    /** Marca del vehiculo */
    private String marca;
    
    /** Modelo del vehiculo */
    private String modelo;
    
    /** Color del vehiculo */
    private String color;
    
    /** Numero de placa */
    private String placa;
    
    /** Ano de fabricacion */
    private Integer anio;
    
    /** Estado del vehiculo (ACTIVO, INACTIVO, MANTENIMIENTO) */
    private String estadoVehiculo;
    
    /** Fecha de registro en el sistema */
    private LocalDateTime fechaRegistro;
}
