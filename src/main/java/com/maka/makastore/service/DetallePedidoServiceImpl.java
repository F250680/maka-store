package com.maka.makastore.service;

import com.maka.makastore.dto.DetallePedidoRequestDTO;
import com.maka.makastore.dto.DetallePedidoResponseDTO;
import com.maka.makastore.model.DetallePedido;
import com.maka.makastore.model.Pedido;
import com.maka.makastore.model.Producto;
import com.maka.makastore.repository.DetallePedidoRepository;
import com.maka.makastore.repository.PedidoRepository;
import com.maka.makastore.repository.ProductoRepository;
import com.maka.makastore.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz DetallePedidoService.
 * Contiene la lógica de negocio para la gestión de detalles de pedido.
 */
@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public DetallePedidoServiceImpl(DetallePedidoRepository detallePedidoRepository,
                                    PedidoRepository pedidoRepository,
                                    ProductoRepository productoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Crea un nuevo detalle de pedido.
     * @param detallePedidoRequestDTO DTO con los datos del detalle de pedido a crear.
     * @return DTO del detalle de pedido creado.
     * @throws ResourceNotFoundException si el pedido o el producto no se encuentran.
     */
    @Override
    @Transactional
    public DetallePedidoResponseDTO createDetallePedido(DetallePedidoRequestDTO detallePedidoRequestDTO) {
        // Validar y obtener el Pedido
        Pedido pedido = pedidoRepository.findById(detallePedidoRequestDTO.getIdPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID " + detallePedidoRequestDTO.getIdPedido() + " no encontrado para el detalle."));

        // Validar y obtener el Producto
        Producto producto = productoRepository.findById(detallePedidoRequestDTO.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + detallePedidoRequestDTO.getIdProducto() + " no encontrado para el detalle."));

        // Mapear DTO a Entidad
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setCantidad(detallePedidoRequestDTO.getCantidad());
        detallePedido.setPrecioUnitario(detallePedidoRequestDTO.getPrecioUnitario());
        detallePedido.setPedido(pedido);
        detallePedido.setProducto(producto);

        // Calcular subtotal
        BigDecimal subtotal = detallePedido.getPrecioUnitario().multiply(BigDecimal.valueOf(detallePedido.getCantidad()));
        detallePedido.setSubtotal(subtotal);

        DetallePedido nuevoDetallePedido = detallePedidoRepository.save(detallePedido);

        // Mapear Entidad a DTO de Respuesta
        return mapDetallePedidoToResponseDTO(nuevoDetallePedido);
    }

    /**
     * Obtiene todos los detalles de pedido.
     * @return Lista de DTOs de detalles de pedido.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoResponseDTO> getAllDetallesPedido() {
        List<DetallePedido> detallesPedido = detallePedidoRepository.findAll();
        return detallesPedido.stream()
                .map(this::mapDetallePedidoToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un detalle de pedido por su ID.
     * @param id ID del detalle de pedido.
     * @return DTO del detalle de pedido si se encuentra.
     * @throws ResourceNotFoundException si el detalle de pedido no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetallePedidoResponseDTO> getDetallePedidoById(Long id) {
        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de pedido con ID " + id + " no encontrado."));
        return Optional.of(mapDetallePedidoToResponseDTO(detallePedido));
    }

    /**
     * Actualiza un detalle de pedido existente.
     * @param id ID del detalle de pedido a actualizar.
     * @param detallePedidoRequestDTO DTO con los datos actualizados del detalle de pedido.
     * @return DTO del detalle de pedido actualizado.
     * @throws ResourceNotFoundException si el detalle de pedido, pedido o producto no se encuentran.
     */
    @Override
    @Transactional
    public Optional<DetallePedidoResponseDTO> updateDetallePedido(Long id, DetallePedidoRequestDTO detallePedidoRequestDTO) {
        DetallePedido existingDetallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de pedido con ID " + id + " no encontrado para actualizar."));

        // Validar y obtener el Pedido (si se cambia o para asegurar que exista)
        Pedido pedido = pedidoRepository.findById(detallePedidoRequestDTO.getIdPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID " + detallePedidoRequestDTO.getIdPedido() + " no encontrado para el detalle."));

        // Validar y obtener el Producto (si se cambia o para asegurar que exista)
        Producto producto = productoRepository.findById(detallePedidoRequestDTO.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + detallePedidoRequestDTO.getIdProducto() + " no encontrado para el detalle."));

        existingDetallePedido.setCantidad(detallePedidoRequestDTO.getCantidad());
        existingDetallePedido.setPrecioUnitario(detallePedidoRequestDTO.getPrecioUnitario());
        existingDetallePedido.setPedido(pedido);
        existingDetallePedido.setProducto(producto);

        // Recalcular subtotal
        BigDecimal subtotal = existingDetallePedido.getPrecioUnitario().multiply(BigDecimal.valueOf(existingDetallePedido.getCantidad()));
        existingDetallePedido.setSubtotal(subtotal);

        DetallePedido updatedDetallePedido = detallePedidoRepository.save(existingDetallePedido);

        return Optional.of(mapDetallePedidoToResponseDTO(updatedDetallePedido));
    }

    /**
     * Elimina un detalle de pedido por su ID.
     * @param id ID del detalle de pedido a eliminar.
     * @return true si el detalle de pedido fue eliminado.
     * @throws ResourceNotFoundException si el detalle de pedido no se encuentra.
     */
    @Override
    @Transactional
    public boolean deleteDetallePedido(Long id) {
        if (!detallePedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Detalle de pedido con ID " + id + " no encontrado para eliminar.");
        }
        detallePedidoRepository.deleteById(id);
        return true;
    }

    /**
     * Método auxiliar para mapear una entidad DetallePedido a un DetallePedidoResponseDTO.
     *
     * @param detallePedido La entidad DetallePedido a mapear.
     * @return Un DetallePedidoResponseDTO.
     */
    private DetallePedidoResponseDTO mapDetallePedidoToResponseDTO(DetallePedido detallePedido) {
        DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO(
                detallePedido.getIdDetallePedido(),
                detallePedido.getCantidad(),
                detallePedido.getPrecioUnitario(),
                detallePedido.getSubtotal(),
                detallePedido.getPedido() != null ? detallePedido.getPedido().getIdPedido() : null,
                detallePedido.getProducto() != null ? detallePedido.getProducto().getIdProducto() : null
        );
        // Obtener el nombre del producto para el DTO de respuesta
        if (detallePedido.getProducto() != null) {
            Optional<Producto> productoOptional = productoRepository.findById(detallePedido.getProducto().getIdProducto());
            productoOptional.ifPresent(producto -> dto.setNombreProducto(producto.getNombre()));
        }
        return dto;
    }
}
