/**
 * DTO ApiResponse - Objeto de respuesta generico para la API
 * 
 * Este DTO proporciona una estructura consistente para todas
 * las respuestas de la API, incluyendo estado, mensaje y datos.
 * 
 * @param <T> Tipo de datos que contiene la respuesta
 */
package com.tropimotos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    /**
     * Indica si la operacion fue exitosa
     * true = exito, false = error
     */
    private boolean success;
    
    /**
     * Mensaje descriptivo de la operacion
     * Ejemplo: "Usuario creado exitosamente", "Error de validacion"
     */
    private String message;
    
    /**
     * Datos de la respuesta (puede ser null en caso de error)
     */
    private T data;

    /**
     * Crea una respuesta exitosa con datos
     * 
     * @param <T> Tipo de los datos
     * @param data Datos a incluir en la respuesta
     * @return ApiResponse con exito y mensaje por defecto
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operación exitosa", data);
    }

    /**
     * Crea una respuesta exitosa con mensaje personalizado y datos
     * 
     * @param <T> Tipo de los datos
     * @param message Mensaje descriptivo
     * @param data Datos a incluir en la respuesta
     * @return ApiResponse con exito y mensaje personalizado
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    /**
     * Crea una respuesta de error
     * 
     * @param <T> Tipo de los datos (siempre null en errores)
     * @param message Mensaje de error descriptivo
     * @return ApiResponse con error
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
