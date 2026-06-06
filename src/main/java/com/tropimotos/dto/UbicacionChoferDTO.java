/**
 * DTO UbicacionChoferDTO - Objeto de transferencia de datos de Ubicacion
 * 
 * Este DTO se utiliza para enviar informacion de ubicacion GPS
 * de los choferes a traves de la API.
 */
package com.tropimotos.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionChoferDTO {
    
    /** Identificador unico de la ubicacion */
    private Integer idUbicacion;
    
    /** ID del chofer */
    private Integer idChofer;
    
    /** Latitud GPS */
    private Double latitud;
    
    /** Longitud GPS */
    private Double longitud;
    
    /** Precision del GPS en metros */
    private Double precisionGps;
    
    /** Fecha y hora de la ubicacion */
    private LocalDateTime fechaHora;
}
