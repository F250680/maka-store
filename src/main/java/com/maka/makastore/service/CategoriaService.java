package com.maka.makastore.service;

import com.maka.makastore.dto.CategoriaRequestDTO;
import com.maka.makastore.dto.CategoriaResponseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para la capa de servicio de Categoria.
 * Define las operaciones de negocio relacionadas con las categorías.
 */
public interface CategoriaService {

    /**
     * Crea una nueva categoría.
     * @param categoriaRequestDTO DTO con los datos de la categoría a crear.
     * @return DTO de la categoría creada.
     */
    CategoriaResponseDTO createCategoria(CategoriaRequestDTO categoriaRequestDTO);

    /**
     * Obtiene todas las categorías.
     * @return Lista de DTOs de categorías.
     */
    List<CategoriaResponseDTO> getAllCategorias();

    /**
     * Obtiene una categoría por su ID.
     * @param id ID de la categoría.
     * @return Optional que contiene el DTO de la categoría si se encuentra, o vacío si no.
     */
    Optional<CategoriaResponseDTO> getCategoriaById(Long id);

    /**
     * Actualiza una categoría existente.
     * @param id ID de la categoría a actualizar.
     * @param categoriaRequestDTO DTO con los datos actualizados de la categoría.
     * @return Optional que contiene el DTO de la categoría actualizada si se encuentra, o vacío si no.
     */
    Optional<CategoriaResponseDTO> updateCategoria(Long id, CategoriaRequestDTO categoriaRequestDTO);

    /**
     * Elimina una categoría por su ID.
     * @param id ID de la categoría a eliminar.
     * @return true si la categoría fue eliminada, false si no se encontró.
     */
    boolean deleteCategoria(Long id);
}
