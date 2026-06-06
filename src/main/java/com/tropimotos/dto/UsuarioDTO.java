/**
 * DTO UsuarioDTO - Objeto de transferencia de datos de Usuario
 * 
 * Este DTO se utiliza para enviar informacion de usuarios
 * a traves de la API sin exponer datos sensibles como la contrasena.
 */
package com.tropimotos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    
    /** Identificador unico del usuario */
    private Integer idUsuario;
    
    /** Identificador del rol del usuario */
    private Integer idRol;
    
    /** Nombre del rol del usuario */
    private String nombreRol;
    
    /** Nombre del usuario */
    private String nombre;
    
    /** Apellido del usuario */
    private String apellido;
    
    /** Numero de telefono del usuario */
    private String telefono;
    
    /** Correo electronico del usuario */
    private String email;
    
    /** URL de la foto de perfil del usuario */
    private String fotoPerfil;
    
    /** Estado de la cuenta (ACTIVO, INACTIVO, SUSPENDIDO) */
    private String estadoCuenta;
}
