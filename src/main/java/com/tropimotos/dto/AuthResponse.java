/**
 * DTO AuthResponse - Objeto de respuesta para operaciones de autenticacion
 * 
 * Este DTO se devuelve cuando un usuario inicia sesion exitosamente.
 * Contiene el token JWT y datos basicos del usuario.
 */
package com.tropimotos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    /**
     * Token JWT de autenticacion
     * Se utiliza para autenticar solicitudes posteriores
     */
    private String token;
    
    /**
     * Tipo de token (siempre "Bearer" para JWT)
     */
    private String tipo;
    
    /**
     * Identificador unico del usuario
     */
    private Integer idUsuario;

    /**
     * Identificador del chofer asociado al usuario autenticado
     * Solo aplica cuando el rol es CHOFER
     */
    private Integer idChofer;

    /**
     * Identificador del vehiculo asociado al chofer autenticado
     * Solo aplica cuando el rol es CHOFER
     */
    private Integer idVehiculo;
    
    /**
     * Nombre completo del usuario
     */
    private String nombre;
    
    /**
     * Correo electronico del usuario
     */
    private String email;
    
    /**
     * Nombre del rol del usuario (ej: CLIENTE, CHOFER, ADMIN)
     */
    private String rol;

    /**
     * Constructor completo para crear la respuesta de autenticacion
     * Inicializa automaticamente el tipo como "Bearer"
     * 
     * @param token Token JWT generado
     * @param idUsuario ID del usuario
     * @param nombre Nombre completo del usuario
     * @param email Email del usuario
     * @param rol Rol del usuario
     */
    public AuthResponse(String token, Integer idUsuario, String nombre, String email, String rol) {
        this.token = token;
        this.tipo = "Bearer";
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public AuthResponse(
            String token,
            Integer idUsuario,
            Integer idChofer,
            Integer idVehiculo,
            String nombre,
            String email,
            String rol
    ) {
        this.token = token;
        this.tipo = "Bearer";
        this.idUsuario = idUsuario;
        this.idChofer = idChofer;
        this.idVehiculo = idVehiculo;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }
}
