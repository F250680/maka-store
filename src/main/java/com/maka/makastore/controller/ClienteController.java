package com.maka.makastore.controller;

import com.maka.makastore.dto.ClienteRequestDTO; // Importa el DTO de petición
import com.maka.makastore.dto.ClienteResponseDTO; // Importa el DTO de respuesta
import com.maka.makastore.service.ClienteService; // Importa el nuevo servicio de clientes

import jakarta.validation.Valid; // Importa la anotación @Valid
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de clientes.
 * Delega la lógica de negocio a la capa de servicio (ClienteService).
 * El manejo de excepciones de validación se realiza globalmente a través de GlobalExceptionHandler.
 */
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/clientes") // Define la ruta base para todos los endpoints de este controlador
@CrossOrigin(origins = "http://localhost:3000") // Permite peticiones desde el frontend en localhost:3000
public class ClienteController {

    private final ClienteService clienteService; // Ahora inyectamos el servicio de clientes

    // Inyección de dependencias a través del constructor (preferido)
    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Crea un nuevo cliente utilizando un DTO de petición.
     * Delega la creación al servicio.
     *
     * @param clienteRequestDTO El DTO con los datos del cliente a crear.
     * @return ResponseEntity con el DTO del cliente creado y estado HTTP 201 (Created).
     */
    @PostMapping // Mapea las peticiones POST a /api/clientes
    public ResponseEntity<ClienteResponseDTO> createCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        // La lógica de mapeo y guardado se ha movido al servicio
        ClienteResponseDTO nuevoClienteDTO = clienteService.createCliente(clienteRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoClienteDTO);
    }

    /**
     * Obtiene la lista de todos los clientes y los devuelve como DTOs de respuesta.
     * Delega la obtención al servicio.
     *
     * @return ResponseEntity con la lista de DTOs de clientes y estado HTTP 200 (OK).
     */
    @GetMapping // Mapea las peticiones GET a /api/clientes
    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes() {
        // La lógica de obtención y mapeo se ha movido al servicio
        List<ClienteResponseDTO> responseDTOs = clienteService.getAllClientes();
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Obtiene un cliente por su ID y lo devuelve como un DTO de respuesta.
     * Delega la obtención al servicio.
     *
     * @param id ID del cliente.
     * @return ResponseEntity con el DTO del cliente encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (Not Found) si no se encuentra.
     */
    @GetMapping("/{id}") // Mapea las peticiones GET a /api/clientes/{id}
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id) {
        // La lógica de obtención y mapeo se ha movido al servicio
        return clienteService.getClienteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualiza un cliente existente utilizando un DTO de petición.
     * Delega la actualización al servicio.
     *
     * @param id ID del cliente a actualizar.
     * @param clienteRequestDTO DTO con los datos actualizados del cliente.
     * @return ResponseEntity con el DTO del cliente actualizado y estado HTTP 200 (OK).
     */
    @PutMapping("/{id}") // Mapea las peticiones PUT a /api/clientes/{id}
    public ResponseEntity<ClienteResponseDTO> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        // La lógica de actualización y mapeo se ha movido al servicio
        return clienteService.updateCliente(id, clienteRequestDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Elimina un cliente por su ID.
     * Delega la eliminación al servicio.
     *
     * @param id ID del cliente a eliminar.
     * @return ResponseEntity con estado HTTP 204 (No Content) si la eliminación es exitosa,
     * o estado HTTP 404 (Not Found) si el cliente no existe.
     */
    @DeleteMapping("/{id}") // Mapea las peticiones DELETE a /api/clientes/{id}
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        // La lógica de eliminación se ha movido al servicio
        boolean deleted = clienteService.deleteCliente(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
