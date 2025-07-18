package com.maka.makastore.service;

import com.maka.makastore.dto.PedidoRequestDTO;
import com.maka.makastore.dto.PedidoResponseDTO;
// Importa el nuevo DTO para detalles anidados (este es el correcto para la petición)
import com.maka.makastore.dto.DetallePedidoNestedDTO;
// Importa el DTO para la respuesta de detalles (este es el correcto para la salida)
import com.maka.makastore.dto.DetallePedidoResponseDTO;

import com.maka.makastore.model.Pedido;
import com.maka.makastore.model.DetallePedido;
import com.maka.makastore.model.Cliente;
import com.maka.makastore.model.Producto;

import com.maka.makastore.repository.PedidoRepository;
import com.maka.makastore.repository.ClienteRepository;
import com.maka.makastore.repository.ProductoRepository;
import com.maka.makastore.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Objects;

/**
 * Implementación de la interfaz PedidoService.
 * Contiene la lógica de negocio para la gestión de pedidos.
 */
@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository,
                             ClienteRepository clienteRepository,
                             ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Crea un nuevo pedido.
     * @param pedidoRequestDTO DTO con los datos del pedido a crear.
     * @return DTO del pedido creado.
     * @throws ResourceNotFoundException si el cliente o algún producto no se encuentra.
     * @throws IllegalArgumentException si el pedido no tiene detalles.
     */
    @Override
    @Transactional
    public PedidoResponseDTO createPedido(PedidoRequestDTO pedidoRequestDTO) {
        // 1. Validar y obtener el Cliente
        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + pedidoRequestDTO.getIdCliente() + " no encontrado."));

        // 2. Mapear DTO a Entidad Pedido
        Pedido pedido = new Pedido();
        pedido.setEstadoPedido(pedidoRequestDTO.getEstadoPedido());
        pedido.setCliente(cliente);
        pedido.setIdCliente(cliente.getIdCliente()); // <--- ¡LÍNEA CLAVE! Asignar el ID del cliente directamente

        // 3. Procesar los detalles del pedido
        BigDecimal totalPedido = BigDecimal.ZERO;
        List<DetallePedido> detalles = new ArrayList<>();
        if (pedidoRequestDTO.getDetalles() != null && !pedidoRequestDTO.getDetalles().isEmpty()) {
            // Iteramos sobre DetallePedidoNestedDTO, que es el tipo correcto del request
            for (DetallePedidoNestedDTO detalleDTO : pedidoRequestDTO.getDetalles()) {
                Producto producto = productoRepository.findById(detalleDTO.getIdProducto())
                        .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + detalleDTO.getIdProducto() + " no encontrado para el detalle."));

                DetallePedido detalle = new DetallePedido();
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
                detalle.setProducto(producto);
                detalle.setPedido(pedido); // Esto es crucial para la relación bidireccional

                BigDecimal subtotalDetalle = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
                detalle.setSubtotal(subtotalDetalle);
                totalPedido = totalPedido.add(subtotalDetalle);

                detalles.add(detalle);
            }
        } else {
            throw new IllegalArgumentException("El pedido debe tener al menos un detalle.");
        }

        pedido.setDetallesPedido(detalles);
        pedido.setTotal(totalPedido);

        Pedido nuevoPedido = pedidoRepository.save(pedido);

        return mapPedidoToResponseDTO(nuevoPedido);
    }

    /**
     * Obtiene todos los pedidos.
     * @return Lista de DTOs de pedidos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(this::mapPedidoToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un pedido por su ID.
     * @param id ID del pedido.
     * @return DTO del pedido si se encuentra.
     * @throws ResourceNotFoundException si el pedido no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoResponseDTO> getPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID " + id + " no encontrado."));
        return Optional.of(mapPedidoToResponseDTO(pedido));
    }

    /**
     * Actualiza un pedido existente.
     * @param id ID del pedido a actualizar.
     * @param pedidoRequestDTO DTO con los datos actualizados del pedido.
     * @return DTO del pedido actualizado.
     * @throws ResourceNotFoundException si el pedido, cliente o algún producto no se encuentra.
     * @throws IllegalArgumentException si el pedido no tiene detalles.
     */
    @Override
    @Transactional
    public Optional<PedidoResponseDTO> updatePedido(Long id, PedidoRequestDTO pedidoRequestDTO) {
        Pedido existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID " + id + " no encontrado para actualizar."));

        // 1. Validar y obtener el Cliente
        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + pedidoRequestDTO.getIdCliente() + " no encontrado."));

        // 2. Actualizar campos del Pedido
        existingPedido.setEstadoPedido(pedidoRequestDTO.getEstadoPedido());
        existingPedido.setCliente(cliente);
        existingPedido.setIdCliente(cliente.getIdCliente()); // <--- ¡LÍNEA CLAVE! Asignar el ID del cliente directamente

        // 3. Procesar y actualizar los detalles del pedido
        // Limpiar los detalles existentes para reemplazarlos con los nuevos
        existingPedido.getDetallesPedido().clear();

        BigDecimal totalPedido = BigDecimal.ZERO;
        if (pedidoRequestDTO.getDetalles() != null && !pedidoRequestDTO.getDetalles().isEmpty()) {
            // Iteramos sobre DetallePedidoNestedDTO, que es el tipo correcto del request
            for (DetallePedidoNestedDTO detalleDTO : pedidoRequestDTO.getDetalles()) {
                Producto producto = productoRepository.findById(detalleDTO.getIdProducto())
                        .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + detalleDTO.getIdProducto() + " no encontrado para el detalle."));

                DetallePedido detalle = new DetallePedido();
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
                detalle.setProducto(producto);
                detalle.setPedido(existingPedido); // Esto es crucial para la relación bidireccional

                BigDecimal subtotalDetalle = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
                detalle.setSubtotal(subtotalDetalle);
                totalPedido = totalPedido.add(subtotalDetalle);

                existingPedido.getDetallesPedido().add(detalle);
            }
        } else {
            throw new IllegalArgumentException("El pedido debe tener al menos un detalle.");
        }

        existingPedido.setTotal(totalPedido);

        Pedido updatedPedido = pedidoRepository.save(existingPedido);

        return Optional.of(mapPedidoToResponseDTO(updatedPedido));
    }

    /**
     * Elimina un pedido por su ID.
     * @param id ID del pedido a eliminar.
     * @return true si el pedido fue eliminado.
     * @throws ResourceNotFoundException si el pedido no se encuentra.
     */
    @Override
    @Transactional
    public boolean deletePedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido con ID " + id + " no encontrado para eliminar.");
        }
        pedidoRepository.deleteById(id);
        return true;
    }

    /**
     * Método auxiliar para mapear una entidad Pedido a un PedidoResponseDTO.
     * Incluye la lógica para obtener el nombre del cliente y mapear los detalles.
     *
     * @param pedido La entidad Pedido a mapear.
     * @return Un PedidoResponseDTO.
     */
    private PedidoResponseDTO mapPedidoToResponseDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO(
                pedido.getIdPedido(),
                pedido.getFechaPedido(),
                pedido.getTotal(),
                pedido.getEstadoPedido(),
                pedido.getCliente() != null ? pedido.getCliente().getIdCliente() : null
        );

        if (pedido.getCliente() != null) {
            Optional<Cliente> clienteOptional = clienteRepository.findById(pedido.getCliente().getIdCliente());
            clienteOptional.ifPresent(cliente -> dto.setNombreCliente(cliente.getNombre()));
        }

        // Mapear los detalles del pedido
        if (pedido.getDetallesPedido() != null) {
            List<DetallePedidoResponseDTO> detallesDTO = pedido.getDetallesPedido().stream()
                    .filter(Objects::nonNull)
                    .map(detalle -> {
                        DetallePedidoResponseDTO detalleDTO = new DetallePedidoResponseDTO(
                                detalle.getIdDetallePedido(),
                                detalle.getCantidad(),
                                detalle.getPrecioUnitario(),
                                detalle.getSubtotal(),
                                detalle.getPedido() != null ? detalle.getPedido().getIdPedido() : null,
                                detalle.getProducto() != null ? detalle.getProducto().getIdProducto() : null
                        );
                        if (detalle.getProducto() != null) {
                            Optional<Producto> productoOptional = productoRepository.findById(detalle.getProducto().getIdProducto());
                            productoOptional.ifPresent(producto -> detalleDTO.setNombreProducto(producto.getNombre()));
                        }
                        return detalleDTO;
                    })
                    .collect(Collectors.toList());
            dto.setDetalles(detallesDTO);
        }
        return dto;
    }
}
