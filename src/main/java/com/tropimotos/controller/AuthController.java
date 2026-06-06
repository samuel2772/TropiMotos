/**
 * Controller AuthController - Endpoints para autenticacion
 * 
 * Este controller maneja las operaciones de login y registro
 * de usuarios en el sistema.
 */
package com.tropimotos.controller;

import com.tropimotos.dto.*;
import com.tropimotos.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *@RestController: Combina @Controller y @ResponseBody
 *               Devuelve datos JSON directamente
 *@RequestMapping: Define la ruta base para todos los endpoints
 *@RequiredArgsConstructor: Lombok - genera constructor con campos final
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    /** Servicio de autenticacion inyectado */
    private final AuthService authService;

    /**
     * Endpoint para iniciar sesion
     * Recibe email y contrasena, retorna token JWT si son validos
     * 
     * @param request Credenciales del usuario
     * @return ResponseEntity con ApiResponse conteniendo AuthResponse
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Intenta autenticar al usuario
            AuthResponse response = authService.login(request);
            // Retorna respuesta exitosa con el token
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            // Retorna error con el mensaje de la excepcion
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Endpoint para registrar un nuevo usuario
     * Crea un usuario con rol CLIENTE por defecto
     * 
     * @param request Datos del usuario a registrar
     * @return ResponseEntity con ApiResponse conteniendo UsuarioDTO
     */
    @PostMapping("/registro")
    public ResponseEntity<ApiResponse<UsuarioDTO>> registro(@Valid @RequestBody RegistroRequest request) {
        try {
            // Registra el nuevo usuario
            UsuarioDTO usuario = authService.registro(request);
            // Retorna respuesta exitosa
            return ResponseEntity.ok(ApiResponse.success("Usuario registrado exitosamente", usuario));
        } catch (Exception e) {
            // Retorna error con el mensaje
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
