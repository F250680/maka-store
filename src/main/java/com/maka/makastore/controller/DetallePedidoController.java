package com.maka.makastore.controller;

import com.maka.makastore.dto.DetallePedidoRequestDTO;
import com.maka.makastore.dto.DetallePedidoResponseDTO;
import com.maka.makastore.service.DetallePedidoService;
import com.maka.makastore.exception.ResourceNotFoundException; // Importa la excepción

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de detalles de pedido.
 * Delega la lógica de negocio a la capa de servicio (DetallePedidoService).
 */
@RestController
@RequestMapping("/api/detalles")
@CrossOrigin(origins = "http://localhost:3000") // Permite peticiones desde el frontend en localhost:3000
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    @Autowired
    public DetallePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    /**
     * Crea un nuevo detalle de pedido.
     *
     * @param detallePedidoRequestDTO DTO con los datos del detalle a crear.
     * @return ResponseEntity con el DTO del detalle creado y estado HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<DetallePedidoResponseDTO> createDetallePedido(@Valid @RequestBody DetallePedidoRequestDTO detallePedidoRequestDTO) {
        DetallePedidoResponseDTO nuevoDetalleDTO = detallePedidoService.createDetallePedido(detallePedidoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetalleDTO);
    }

    /**
     * Obtiene todos los detalles de pedido.
     *
     * @return ResponseEntity con la lista de DTOs de detalles y estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<DetallePedidoResponseDTO>> getAllDetallesPedido() {
        List<DetallePedidoResponseDTO> responseDTOs = detallePedidoService.getAllDetallesPedido();
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Obtiene un detalle de pedido por su ID.
     *
     * @param id ID del detalle de pedido.
     * @return ResponseEntity con el DTO del detalle encontrado y estado HTTP 200 (OK).
     * @throws ResourceNotFoundException si el detalle de pedido no se encuentra (manejado globalmente).
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDTO> getDetallePedidoById(@PathVariable Long id) {
        // El servicio lanzará ResourceNotFoundException si no se encuentra.
        // El GlobalExceptionHandler lo capturará y devolverá un 404 con mensaje.
        DetallePedidoResponseDTO dto = detallePedidoService.getDetallePedidoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de pedido con ID " + id + " no encontrado."));
        return ResponseEntity.ok(dto);
    }

    /**
     * Actualiza un detalle de pedido existente.
     *
     * @param id ID del detalle de pedido a actualizar.
     * @param detallePedidoRequestDTO DTO con los datos actualizados.
     * @return ResponseEntity con el DTO del detalle actualizado y estado HTTP 200 (OK).
     * @throws ResourceNotFoundException si el detalle no se encuentra (manejado globalmente).
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDTO> updateDetallePedido(@PathVariable Long id, @Valid @RequestBody DetallePedidoRequestDTO detallePedidoRequestDTO) {
        // El servicio lanzará ResourceNotFoundException si no se encuentra.
        // El GlobalExceptionHandler lo capturará y devolverá un 404 con mensaje.
        DetallePedidoResponseDTO dto = detallePedidoService.updateDetallePedido(id, detallePedidoRequestDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de pedido con ID " + id + " no encontrado para actualizar."));
        return ResponseEntity.ok(dto);
    }

    /**
     * Elimina un detalle de pedido por su ID.
     *
     * @param id ID del detalle de pedido a eliminar.
     * @return ResponseEntity con estado HTTP 204 (No Content).
     * @throws ResourceNotFoundException si el detalle no se encuentra (manejado globalmente).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetallePedido(@PathVariable Long id) {
        // El servicio lanzará ResourceNotFoundException si no se encuentra.
        // El GlobalExceptionHandler lo capturará y devolverá un 404 con mensaje.
        boolean deleted = detallePedidoService.deleteDetallePedido(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            // Esto en teoría no debería ocurrir si el servicio ya lanzó ResourceNotFoundException
            // pero es un fallback.
            throw new ResourceNotFoundException("Detalle de pedido con ID " + id + " no encontrado para eliminar.");
        }
    }
}
