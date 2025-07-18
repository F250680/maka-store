package com.maka.makastore.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para enviar datos de un Detalle de Pedido como respuesta de la API.
 * Define la estructura de los datos que el cliente recibirá.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoResponseDTO {

    private Long idDetallePedido;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private Long idPedido; // ID del pedido asociado
    private Long idProducto; // ID del producto asociado
    private String nombreProducto; // Nombre del producto para una mejor visualización

    // Constructor para mapear desde la entidad DetallePedido
    public DetallePedidoResponseDTO(Long idDetallePedido, int cantidad, BigDecimal precioUnitario,
                                    BigDecimal subtotal, Long idPedido, Long idProducto) {
        this.idDetallePedido = idDetallePedido;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
    }
}
