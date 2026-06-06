/**
 * Interfaz AuthService - Define los metodos para autenticacion de usuarios
 * 
 * Esta interfaz establece el contrato para las operaciones de
 * inicio de sesion y registro de usuarios en el sistema.
 */
package com.tropimotos.service;

import com.tropimotos.dto.AuthResponse;
import com.tropimotos.dto.LoginRequest;
import com.tropimotos.dto.RegistroRequest;
import com.tropimotos.dto.UsuarioDTO;

/**
 * Interfaz que define los metodos de autenticacion
 * La implementacion se encuentra en AuthServiceImpl
 */
public interface AuthService {
    
    /**
     * Autentica un usuario con email y contrasena
     * 
     * @param request Objeto con las credenciales del usuario
     * @return AuthResponse con el token JWT y datos del usuario
     * @throws RuntimeException si las credenciales son invalidas
     */
    AuthResponse login(LoginRequest request);
    
    /**
     * Registra un nuevo usuario en el sistema
     * Asigna automaticamente el rol CLIENTE
     * 
     * @param request Objeto con los datos del nuevo usuario
     * @return UsuarioDTO con los datos del usuario creado
     * @throws RuntimeException si el email ya esta registrado
     */
    UsuarioDTO registro(RegistroRequest request);
}
