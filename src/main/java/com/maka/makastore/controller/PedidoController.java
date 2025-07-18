package com.maka.makastore.controller;

import com.maka.makastore.dto.PedidoRequestDTO; // Importa el DTO de petición
import com.maka.makastore.dto.PedidoResponseDTO; // Importa el DTO de respuesta
import com.maka.makastore.service.PedidoService; // Importa el nuevo servicio de pedidos

import jakarta.validation.Valid; // Importa la anotación @Valid
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de pedidos.
 * Delega la lógica de negocio a la capa de servicio (PedidoService).
 * El manejo de excepciones de validación se realiza globalmente a través de GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:3000") // Mantener CORS si es necesario
public class PedidoController {

    private final PedidoService pedidoService; // Ahora inyectamos el servicio

    // Inyección de dependencias a través del constructor (preferido)
    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * Crea un nuevo pedido utilizando un DTO de petición.
     * Delega la creación al servicio.
     *
     * @param pedidoRequestDTO El DTO con los datos del pedido a crear.
     * @return ResponseEntity con el DTO del pedido creado y estado HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        // La lógica de mapeo y guardado se ha movido al servicio
        PedidoResponseDTO nuevoPedidoDTO = pedidoService.createPedido(pedidoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedidoDTO);
    }

    /**
     * Obtiene la lista de todos los pedidos y los devuelve como DTOs de respuesta.
     * Delega la obtención al servicio.
     *
     * @return ResponseEntity con la lista de DTOs de pedidos y estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        // La lógica de obtención y mapeo se ha movido al servicio
        List<PedidoResponseDTO> responseDTOs = pedidoService.getAllPedidos();
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Obtiene un pedido por su ID y lo devuelve como un DTO de respuesta.
     * Delega la obtención al servicio.
     *
     * @param id ID del pedido.
     * @return ResponseEntity con el DTO del pedido encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (Not Found) si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPorId(@PathVariable Long id) {
        // La lógica de obtención y mapeo se ha movido al servicio
        return pedidoService.getPedidoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualiza un pedido existente utilizando un DTO de petición.
     * Delega la actualización al servicio.
     *
     * @param id ID del pedido a actualizar.
     * @param pedidoRequestDTO DTO con los datos actualizados del pedido.
     * @return ResponseEntity con el DTO del pedido actualizado y estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        // La lógica de actualización y mapeo se ha movido al servicio
        return pedidoService.updatePedido(id, pedidoRequestDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Elimina un pedido por su ID.
     * Delega la eliminación al servicio.
     *
     * @param id ID del pedido a eliminar.
     * @return ResponseEntity con estado HTTP 204 (No Content) si la eliminación es exitosa,
     * o estado HTTP 404 (Not Found) si el pedido no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // La lógica de eliminación se ha movido al servicio
        boolean deleted = pedidoService.deletePedido(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}