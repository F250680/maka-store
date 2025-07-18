package com.maka.makastore.service;

import com.maka.makastore.dto.DetallePedidoRequestDTO;
import com.maka.makastore.dto.DetallePedidoResponseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para la capa de servicio de DetallePedido.
 * Define las operaciones de negocio relacionadas con los detalles de pedido.
 */
public interface DetallePedidoService {

    /**
     * Crea un nuevo detalle de pedido.
     * @param detallePedidoRequestDTO DTO con los datos del detalle de pedido a crear.
     * @return DTO del detalle de pedido creado.
     */
    DetallePedidoResponseDTO createDetallePedido(DetallePedidoRequestDTO detallePedidoRequestDTO);

    /**
     * Obtiene todos los detalles de pedido.
     * @return Lista de DTOs de detalles de pedido.
     */
    List<DetallePedidoResponseDTO> getAllDetallesPedido();

    /**
     * Obtiene un detalle de pedido por su ID.
     * @param id ID del detalle de pedido.
     * @return Optional que contiene el DTO del detalle de pedido si se encuentra, o vacío si no.
     */
    Optional<DetallePedidoResponseDTO> getDetallePedidoById(Long id);

    /**
     * Actualiza un detalle de pedido existente.
     * @param id ID del detalle de pedido a actualizar.
     * @param detallePedidoRequestDTO DTO con los datos actualizados del detalle de pedido.
     * @return Optional que contiene el DTO del detalle de pedido actualizado si se encuentra, o vacío si no.
     */
    Optional<DetallePedidoResponseDTO> updateDetallePedido(Long id, DetallePedidoRequestDTO detallePedidoRequestDTO);

    /**
     * Elimina un detalle de pedido por su ID.
     * @param id ID del detalle de pedido a eliminar.
     * @return true si el detalle de pedido fue eliminado, false si no se encontró.
     */
    boolean deleteDetallePedido(Long id);
}
