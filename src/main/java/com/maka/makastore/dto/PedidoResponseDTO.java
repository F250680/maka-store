package com.maka.makastore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para enviar datos de un Pedido como respuesta de la API.
 * Define la estructura de los datos que el cliente recibirá, incluyendo detalles.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    private Long idPedido;
    private LocalDateTime fechaPedido;
    private BigDecimal total;
    private String estadoPedido;
    private Long idCliente;
    private String nombreCliente; // Nombre del cliente para una mejor visualización
    private List<DetallePedidoResponseDTO> detalles; // Lista de DTOs de detalles

    // Constructor para mapear desde la entidad Pedido (sin detalles ni nombre de cliente inicialmente)
    public PedidoResponseDTO(Long idPedido, LocalDateTime fechaPedido, BigDecimal total,
                             String estadoPedido, Long idCliente) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.total = total;
        this.estadoPedido = estadoPedido;
        this.idCliente = idCliente;
    }
}
