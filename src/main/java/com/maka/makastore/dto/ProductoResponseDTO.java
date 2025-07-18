package com.maka.makastore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO (Data Transfer Object) para enviar datos de un Producto como respuesta de la API.
 * Define la estructura de los datos que el cliente recibirá.
 */
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class ProductoResponseDTO {

    private Long idProducto;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private String descripcion;
    private Long idCategoria; // Se mantiene el ID de categoría
    private String imagen;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String nombreCategoria; // Campo adicional para el nombre de la categoría

    // El constructor manual ha sido eliminado.
    // @AllArgsConstructor de Lombok ya genera un constructor con todos los campos.
}
