package com.maka.makastore.service;

import com.maka.makastore.dto.ClienteRequestDTO;
import com.maka.makastore.dto.ClienteResponseDTO;
import com.maka.makastore.model.Cliente;
import com.maka.makastore.repository.ClienteRepository;
import com.maka.makastore.exception.ResourceNotFoundException; // Importa la nueva excepción

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz ClienteService.
 * Contiene la lógica de negocio para la gestión de clientes.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Crea un nuevo cliente.
     * @param clienteRequestDTO DTO con los datos del cliente a crear.
     * @return DTO del cliente creado.
     */
    @Override
    @Transactional
    public ClienteResponseDTO createCliente(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteRequestDTO.getNombre());
        cliente.setApellido(clienteRequestDTO.getApellido());
        cliente.setEmail(clienteRequestDTO.getEmail());
        cliente.setTelefono(clienteRequestDTO.getTelefono());
        cliente.setDireccion(clienteRequestDTO.getDireccion());

        Cliente nuevoCliente = clienteRepository.save(cliente);

        return mapClienteToResponseDTO(nuevoCliente);
    }

    /**
     * Obtiene todos los clientes.
     * @return Lista de DTOs de clientes.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::mapClienteToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un cliente por su ID.
     * @param id ID del cliente.
     * @return DTO del cliente si se encuentra.
     * @throws ResourceNotFoundException si el cliente no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteResponseDTO> getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado."));
        return Optional.of(mapClienteToResponseDTO(cliente));
    }

    /**
     * Actualiza un cliente existente.
     * @param id ID del cliente a actualizar.
     * @param clienteRequestDTO DTO con los datos actualizados del cliente.
     * @return DTO del cliente actualizado.
     * @throws ResourceNotFoundException si el cliente no se encuentra.
     */
    @Override
    @Transactional
    public Optional<ClienteResponseDTO> updateCliente(Long id, ClienteRequestDTO clienteRequestDTO) {
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado para actualizar."));

        existingCliente.setNombre(clienteRequestDTO.getNombre());
        existingCliente.setApellido(clienteRequestDTO.getApellido());
        existingCliente.setEmail(clienteRequestDTO.getEmail());
        existingCliente.setTelefono(clienteRequestDTO.getTelefono());
        existingCliente.setDireccion(clienteRequestDTO.getDireccion());

        Cliente updatedCliente = clienteRepository.save(existingCliente);

        return Optional.of(mapClienteToResponseDTO(updatedCliente));
    }

    /**
     * Elimina un cliente por su ID.
     * @param id ID del cliente a eliminar.
     * @return true si el cliente fue eliminado.
     * @throws ResourceNotFoundException si el cliente no se encuentra.
     */
    @Override
    @Transactional
    public boolean deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente con ID " + id + " no encontrado para eliminar.");
        }
        clienteRepository.deleteById(id);
        return true;
    }

    /**
     * Método auxiliar para mapear una entidad Cliente a un ClienteResponseDTO.
     *
     * @param cliente La entidad Cliente a mapear.
     * @return Un ClienteResponseDTO.
     */
    private ClienteResponseDTO mapClienteToResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getDireccion()
        );
    }
}
