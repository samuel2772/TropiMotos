/**
 * DTO RegistroRequest - Objeto para recibir datos de registro de usuario
 * 
 * Este DTO se utiliza para recibir los datos necesarios
 * cuando un nuevo usuario desea registrarse en la aplicacion.
 */
package com.tropimotos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class RegistroRequest {
    
    /**
     * Nombre del usuario
     * Campo obligatorio
     */
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    /**
     * Apellido del usuario
     * Campo obligatorio
     */
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    /**
     * Numero de telefono del usuario
     * Campo obligatorio
     */
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    /**
     * Correo electronico del usuario
     * Campo obligatorio y debe tener formato valido de email
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;

    /**
     * Contrasena del usuario
     * Campo obligatorio
     */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
