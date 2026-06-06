/**
 * TropiMotosApplication - Punto de entrada principal de la aplicación Spring Boot
 * 
 * Esta clase inicia el contexto de Spring y levanta el servidor embebido.
 */
package com.tropimotos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Anotacion @SpringBootApplication que combina:
 * - @Configuration: Permite definir beans de configuracion
 * - @EnableAutoConfiguration: Habilita la configuracion automatica de Spring
 * - @ComponentScan: Escanea paquetes por componentes (@Controller, @Service, etc.)
 */
@SpringBootApplication
public class TropiMotosApplication {
    
    /**
     * Metodo principal que lanza la aplicacion Spring Boot.
     * 
     * @param args Argumentos de linea de comandos
     */
    public static void main(String[] args) {
        // Ejecuta la aplicacion Spring Boot
        SpringApplication.run(TropiMotosApplication.class, args);
    }
}
