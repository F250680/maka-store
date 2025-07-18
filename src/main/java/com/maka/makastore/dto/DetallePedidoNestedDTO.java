package com.maka.makastore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para representar un detalle de pedido anidado dentro de un PedidoRequestDTO.
 * No incluye el idPedido, ya que se asume que pertenece al pedido padre que se está creando/actualizando.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoNestedDTO {

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio unitario debe ser un valor positivo")
    private BigDecimal precioUnitario;

    @NotNull(message = "El ID del producto es obligatorio para el detalle")
    @Positive(message = "El ID del producto debe ser un valor positivo")
    private Long idProducto;
}
