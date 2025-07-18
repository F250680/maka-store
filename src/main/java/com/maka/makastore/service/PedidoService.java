package com.maka.makastore.service;

import com.maka.makastore.dto.PedidoRequestDTO;
import com.maka.makastore.dto.PedidoResponseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para la capa de servicio de Pedido.
 * Define las operaciones de negocio relacionadas con los pedidos.
 */
public interface PedidoService {

    /**
     * Crea un nuevo pedido.
     * @param pedidoRequestDTO DTO con los datos del pedido a crear.
     * @return DTO del pedido creado.
     */
    PedidoResponseDTO createPedido(PedidoRequestDTO pedidoRequestDTO);

    /**
     * Obtiene todos los pedidos.
     * @return Lista de DTOs de pedidos.
     */
    List<PedidoResponseDTO> getAllPedidos();

    /**
     * Obtiene un pedido por su ID.
     * @param id ID del pedido.
     * @return Optional que contiene el DTO del pedido si se encuentra, o vacío si no.
     */
    Optional<PedidoResponseDTO> getPedidoById(Long id);

    /**
     * Actualiza un pedido existente.
     * @param id ID del pedido a actualizar.
     * @param pedidoRequestDTO DTO con los datos actualizados del pedido.
     * @return Optional que contiene el DTO del pedido actualizado si se encuentra, o vacío si no.
     */
    Optional<PedidoResponseDTO> updatePedido(Long id, PedidoRequestDTO pedidoRequestDTO);

    /**
     * Elimina un pedido por su ID.
     * @param id ID del pedido a eliminar.
     * @return true si el pedido fue eliminado, false si no se encontró.
     */
    boolean deletePedido(Long id);
}
