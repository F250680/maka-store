package com.maka.makastore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for receiving creation or update data for an Order.
 * Includes validations and a list of order details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotNull(message = "El estado del pedido es obligatorio")
    private String estadoPedido;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser un valor positivo")
    private Long idCliente;

    @Valid // Validates each DetallePedidoNestedDTO in the list
    @NotNull(message = "Los detalles del pedido no pueden ser nulos")
    @Size(min = 1, message = "El pedido debe tener al menos un detalle")
    // Changed DetallePedidoRequestDTO to DetallePedidoNestedDTO
    private List<DetallePedidoNestedDTO> detalles;
}
