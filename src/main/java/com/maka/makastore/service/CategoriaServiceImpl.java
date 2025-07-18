package com.maka.makastore.service;

import com.maka.makastore.dto.CategoriaRequestDTO;
import com.maka.makastore.dto.CategoriaResponseDTO;
import com.maka.makastore.model.Categoria;
import com.maka.makastore.repository.CategoriaRepository;
import com.maka.makastore.exception.ResourceNotFoundException; // Importa la excepción personalizada

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz CategoriaService.
 * Contiene la lógica de negocio para la gestión de categorías.
 */
@Service // Indica que esta clase es un componente de servicio de Spring
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    // Inyección de dependencias a través del constructor (preferido)
    @Autowired
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Crea una nueva categoría.
     * @param categoriaRequestDTO DTO con los datos de la categoría a crear.
     * @return DTO de la categoría creada.
     */
    @Override
    @Transactional // Asegura que la operación se ejecute dentro de una transacción
    public CategoriaResponseDTO createCategoria(CategoriaRequestDTO categoriaRequestDTO) {
        // Mapear DTO a Entidad
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaRequestDTO.getNombre());
        categoria.setDescripcion(categoriaRequestDTO.getDescripcion());

        Categoria nuevaCategoria = categoriaRepository.save(categoria); // Guarda la categoría

        // Mapear Entidad a DTO de Respuesta
        return mapCategoriaToResponseDTO(nuevaCategoria);
    }

    /**
     * Obtiene todas las categorías.
     * @return Lista de DTOs de categorías.
     */
    @Override
    @Transactional(readOnly = true) // Transacción de solo lectura para mejor rendimiento
    public List<CategoriaResponseDTO> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        // Mapear cada entidad Categoria a un CategoriaResponseDTO
        return categorias.stream()
                .map(this::mapCategoriaToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una categoría por su ID.
     * @param id ID de la categoría.
     * @return DTO de la categoría si se encuentra.
     * @throws ResourceNotFoundException si la categoría no se encuentra.
     */
    @Override
    @Transactional(readOnly = true) // Transacción de solo lectura
    public Optional<CategoriaResponseDTO> getCategoriaById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + id + " no encontrada."));
        return Optional.of(mapCategoriaToResponseDTO(categoria));
    }

    /**
     * Actualiza una categoría existente.
     * @param id ID de la categoría a actualizar.
     * @param categoriaRequestDTO DTO con los datos actualizados de la categoría.
     * @return DTO de la categoría actualizada.
     * @throws ResourceNotFoundException si la categoría no se encuentra.
     */
    @Override
    @Transactional // Asegura que la operación se ejecute dentro de una transacción
    public Optional<CategoriaResponseDTO> updateCategoria(Long id, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria existingCategoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + id + " no encontrada para actualizar."));

        existingCategoria.setNombre(categoriaRequestDTO.getNombre());
        existingCategoria.setDescripcion(categoriaRequestDTO.getDescripcion());

        Categoria updatedCategoria = categoriaRepository.save(existingCategoria); // Guarda los cambios (actualiza)

        // Mapear Entidad actualizada a DTO de Respuesta
        return Optional.of(mapCategoriaToResponseDTO(updatedCategoria));
    }

    /**
     * Elimina una categoría por su ID.
     * @param id ID de la categoría a eliminar.
     * @return true si la categoría fue eliminada.
     * @throws ResourceNotFoundException si la categoría no se encuentra.
     */
    @Override
    @Transactional // Asegura que la operación se ejecute dentro de una transacción
    public boolean deleteCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría con ID " + id + " no encontrada para eliminar.");
        }
        categoriaRepository.deleteById(id);
        return true;
    }

    /**
     * Método auxiliar para mapear una entidad Categoria a un CategoriaResponseDTO.
     *
     * @param categoria La entidad Categoria a mapear.
     * @return Un CategoriaResponseDTO.
     */
    private CategoriaResponseDTO mapCategoriaToResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getIdCategoria(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}
