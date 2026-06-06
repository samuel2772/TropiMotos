/**
 * DTO ChoferDTO - Objeto de transferencia de datos de Chofer
 * 
 * Este DTO se utiliza para enviar informacion de chofers
 * a traves de la API, incluyendo datos del usuario asociado.
 */
package com.tropimotos.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChoferDTO {
    
    /** Identificador unico del chofer */
    private Integer idChofer;
    
    /** ID del usuario asociado al chofer */
    private Integer idUsuario;
    
    /** Nombre del usuario */
    private String nombreUsuario;
    
    /** Apellido del usuario */
    private String apellidoUsuario;
    
    /** Telefono del usuario */
    private String telefonoUsuario;
    
    /** Email del usuario */
    private String emailUsuario;
    
    /** URL de la foto de perfil */
    private String fotoPerfil;
    
    /** Numero de licencia de conducir */
    private String numeroLicencia;
    
    /** Cedula de identidad */
    private String ci;
    
    /** Indica si el chofer esta disponible */
    private Boolean disponible;
    
    /** Indica si el chofer esta verificado */
    private Boolean verificado;
    
    /** Estado del chofer (ACTIVO, INACTIVO, SUSPENDIDO) */
    private String estadoChofer;
    
    /** Fecha de registro en el sistema */
    private LocalDateTime fechaRegistro;
}
