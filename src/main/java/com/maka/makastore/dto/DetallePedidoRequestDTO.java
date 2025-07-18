package com.maka.makastore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para recibir datos de creación o actualización de un Detalle de Pedido.
 * Incluye validaciones para la entrada de datos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoRequestDTO {

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio unitario debe ser un valor positivo")
    private BigDecimal precioUnitario;

    // El subtotal no se incluye aquí, se calcula en el backend.

    @NotNull(message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser un valor positivo")
    private Long idPedido; // Recibimos solo el ID del Pedido

    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El ID del producto debe ser un valor positivo")
    private Long idProducto; // Recibimos solo el ID del Producto
}
