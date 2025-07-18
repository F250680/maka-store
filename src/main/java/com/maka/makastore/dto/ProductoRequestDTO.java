package com.maka.makastore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO (Data Transfer Object) para recibir datos de creación o actualización de un Producto.
 * Incluye anotaciones de validación para asegurar la integridad de los datos de entrada.
 */
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio") // Valida que el nombre no sea nulo ni vacío (después de trim)
    private String nombre;

    @NotNull(message = "El precio del producto es obligatorio") // Valida que el precio no sea nulo
    @Positive(message = "El precio debe ser un valor positivo") // Valida que el precio sea mayor que cero
    private BigDecimal precio;

    @Min(value = 0, message = "El stock no puede ser negativo") // Valida que el stock no sea menor que 0
    private Integer stock; // No es @NotNull porque puede ser nulo y se le asigna un valor por defecto en el servicio/controlador

    private String descripcion;

    @NotNull(message = "La categoría del producto es obligatoria") // Valida que el ID de categoría no sea nulo
    @Positive(message = "El ID de categoría debe ser un valor positivo") // Valida que el ID de categoría sea positivo
    private Long idCategoria;

    private String imagen;

    // Las fechas de creación/actualización no se incluyen aquí porque se generan en el backend.
    // La relación 'categoria' tampoco se incluye, ya que recibimos solo el 'idCategoria'.
}
