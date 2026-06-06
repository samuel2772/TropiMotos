/**
 * DTO ChoferRequest - Objeto para recibir datos de registro de chofer
 * 
 * Este DTO se utiliza para recibir los datos necesarios
 * cuando se registra un nuevo chofer en el sistema.
 */
package com.tropimotos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class ChoferRequest {
    
    /**
     * ID del usuario que sera convertido en chofer
     */
    private Integer idUsuario;

    /**
     * Numero de licencia de conducir del chofer
     * Campo obligatorio
     */
    @NotBlank(message = "El número de licencia es obligatorio")
    private String numeroLicencia;

    /**
     * Cedula de identidad del chofer
     * Campo obligatorio
     */
    @NotBlank(message = "La cédula de identidad es obligatoria")
    private String ci;
}
