/**
 * GlobalExceptionHandler - Manejador global de excepciones
 * 
 * Intercepta todas las excepciones lanzadas por los controllers
 * y las convierte en respuestas JSON consistentes.
 */
package com.tropimotos.config;

import com.tropimotos.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 *@RestControllerAdvice: Combina @ControllerAdvice y @ResponseBody
 *                    Aplica a todos los controllers
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de validacion de datos (@Valid)
     * Recoge todos los errores de validacion y los devuelve en un mapa
     * 
     * @param ex Excepcion de validacion
     * @return Respuesta con mapa de errores
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        // Itera sobre todos los errores de validacion
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // Obtiene el nombre del campo que fallo
            String nombreCampo = ((FieldError) error).getField();
            // Obtiene el mensaje de error definido en las anotaciones
            String mensajeError = error.getDefaultMessage();
            errores.put(nombreCampo, mensajeError);
        });
        return ResponseEntity.badRequest().body(ApiResponse.error("Error de validación"));
    }

    /**
     * Maneja excepciones de credenciales invalidas
     * Usado por Spring Security cuando fallan las credenciales
     * 
     * @param ex Excepcion de credenciales invalidas
     * @return Respuesta con codigo 401
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> manejarBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Credenciales inválidas"));
    }

    /**
     * Maneja excepciones RuntimeException genericas
     * Incluye mensajes personalizados de errores de negocio
     * 
     * @param ex Excepcion runtime
     * @return Respuesta con mensaje de error
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> manejarRuntime(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
    }

    /**
     * Maneja cualquier otra excepcion no contemplada
     * Devuelve un error generico de servidor interno
     * 
     * @param ex Excepcion general
     * @return Respuesta con codigo 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> manejarGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error interno del servidor"));
    }
}
