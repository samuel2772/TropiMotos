/**
 * Entidad EstadoViaje - Representa los posibles estados de un viaje
 * 
 * Define los estados por los que puede pasar un viaje desde que se solicita
 * hasta que se completa o cancela.
 * Mapea la tabla 'estados_viaje' de la base de datos PostgreSQL.
 */
package com.tropimotos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estados_viaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadoViaje {

    /**
     * Identificador unico del estado (Primary Key)
     * Se genera automaticamente
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

    /**
     * Nombre del estado del viaje
     * Ejemplos: SOLICITADO, ACEPTADO, EN_CURSO, COMPLETADO, CANCELADO
     * Campo obligatorio
     */
    @Column(name = "nombre_estado", nullable = false, length = 50)
    private String nombreEstado;

    /**
     * Descripcion detallada del estado
     * Proporciona informacion adicional sobre el significado del estado
     */
    @Column(name = "descripcion", length = 255)
    private String descripcion;
}
