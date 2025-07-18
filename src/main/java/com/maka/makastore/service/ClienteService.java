package com.maka.makastore.service;

import com.maka.makastore.dto.ClienteRequestDTO;
import com.maka.makastore.dto.ClienteResponseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para la capa de servicio de Cliente.
 * Define las operaciones de negocio relacionadas con los clientes.
 */
public interface ClienteService {

    /**
     * Crea un nuevo cliente.
     * @param clienteRequestDTO DTO con los datos del cliente a crear.
     * @return DTO del cliente creado.
     */
    ClienteResponseDTO createCliente(ClienteRequestDTO clienteRequestDTO);

    /**
     * Obtiene todos los clientes.
     * @return Lista de DTOs de clientes.
     */
    List<ClienteResponseDTO> getAllClientes();

    /**
     * Obtiene un cliente por su ID.
     * @param id ID del cliente.
     * @return Optional que contiene el DTO del cliente si se encuentra, o vacío si no.
     */
    Optional<ClienteResponseDTO> getClienteById(Long id);

    /**
     * Actualiza un cliente existente.
     * @param id ID del cliente a actualizar.
     * @param clienteRequestDTO DTO con los datos actualizados del cliente.
     * @return Optional que contiene el DTO del cliente actualizado si se encuentra, o vacío si no.
     */
    Optional<ClienteResponseDTO> updateCliente(Long id, ClienteRequestDTO clienteRequestDTO);

    /**
     * Elimina un cliente por su ID.
     * @param id ID del cliente a eliminar.
     * @return true si el cliente fue eliminado, false si no se encontró.
     */
    boolean deleteCliente(Long id);
}
