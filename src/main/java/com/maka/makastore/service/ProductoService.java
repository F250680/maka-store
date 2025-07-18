package com.maka.makastore.service;

import com.maka.makastore.dto.ProductoRequestDTO;
import com.maka.makastore.dto.ProductoResponseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para la capa de servicio de Producto.
 * Define las operaciones de negocio relacionadas con los productos.
 */
public interface ProductoService {

    /**
     * Crea un nuevo producto.
     * @param productoRequestDTO DTO con los datos del producto a crear.
     * @return DTO del producto creado.
     */
    ProductoResponseDTO createProducto(ProductoRequestDTO productoRequestDTO);

    /**
     * Obtiene todos los productos.
     * @return Lista de DTOs de productos.
     */
    List<ProductoResponseDTO> getAllProductos();

    /**
     * Obtiene un producto por su ID.
     * @param id ID del producto.
     * @return Optional que contiene el DTO del producto si se encuentra, o vacío si no.
     */
    Optional<ProductoResponseDTO> getProductoById(Long id);

    /**
     * Actualiza un producto existente.
     * @param id ID del producto a actualizar.
     * @param productoRequestDTO DTO con los datos actualizados del producto.
     * @return Optional que contiene el DTO del producto actualizado si se encuentra, o vacío si no.
     */
    Optional<ProductoResponseDTO> updateProducto(Long id, ProductoRequestDTO productoRequestDTO);

    /**
     * Elimina un producto por su ID.
     * @param id ID del producto a eliminar.
     * @return true si el producto fue eliminado, false si no se encontró.
     */
    boolean deleteProducto(Long id);
}
