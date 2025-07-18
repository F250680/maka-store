package com.maka.makastore.service;

import com.maka.makastore.dto.ProductoRequestDTO;
import com.maka.makastore.dto.ProductoResponseDTO;
import com.maka.makastore.model.Producto;
import com.maka.makastore.model.Categoria;
import com.maka.makastore.repository.ProductoRepository;
import com.maka.makastore.repository.CategoriaRepository;
import com.maka.makastore.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz ProductoService.
 * Contiene la lógica de negocio para la gestión de productos.
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Crea un nuevo producto.
     * @param productoRequestDTO DTO con los datos del producto a crear.
     * @return DTO del producto creado.
     */
    @Override
    @Transactional
    public ProductoResponseDTO createProducto(ProductoRequestDTO productoRequestDTO) {
        // Validar que la categoría exista antes de crear el producto
        categoriaRepository.findById(productoRequestDTO.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + productoRequestDTO.getIdCategoria() + " no encontrada."));

        Producto producto = new Producto();
        producto.setNombre(productoRequestDTO.getNombre());
        producto.setPrecio(productoRequestDTO.getPrecio());
        producto.setStock(productoRequestDTO.getStock() != null ? productoRequestDTO.getStock() : 0);
        producto.setDescripcion(productoRequestDTO.getDescripcion());
        producto.setIdCategoria(productoRequestDTO.getIdCategoria());
        producto.setImagen(productoRequestDTO.getImagen());

        Producto nuevoProducto = productoRepository.save(producto);

        return mapProductoToResponseDTO(nuevoProducto);
    }

    /**
     * Obtiene todos los productos.
     * @return Lista de DTOs de productos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> getAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::mapProductoToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un producto por su ID.
     * @param id ID del producto.
     * @return DTO del producto si se encuentra.
     * @throws ResourceNotFoundException si el producto no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoResponseDTO> getProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + id + " no encontrado."));
        return Optional.of(mapProductoToResponseDTO(producto));
    }

    /**
     * Actualiza un producto existente.
     * @param id ID del producto a actualizar.
     * @param productoRequestDTO DTO con los datos actualizados del producto.
     * @return DTO del producto actualizado.
     * @throws ResourceNotFoundException si el producto no se encuentra.
     */
    @Override
    @Transactional
    public Optional<ProductoResponseDTO> updateProducto(Long id, ProductoRequestDTO productoRequestDTO) {
        Producto existingProducto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + id + " no encontrado para actualizar."));

        // Validar que la nueva categoría exista si se proporciona
        if (productoRequestDTO.getIdCategoria() != null) {
            categoriaRepository.findById(productoRequestDTO.getIdCategoria())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + productoRequestDTO.getIdCategoria() + " no encontrada para el producto."));
        }

        existingProducto.setNombre(productoRequestDTO.getNombre());
        existingProducto.setPrecio(productoRequestDTO.getPrecio());
        existingProducto.setStock(productoRequestDTO.getStock() != null ? productoRequestDTO.getStock() : 0);
        existingProducto.setDescripcion(productoRequestDTO.getDescripcion());
        existingProducto.setIdCategoria(productoRequestDTO.getIdCategoria());
        existingProducto.setImagen(productoRequestDTO.getImagen());

        Producto productoActualizado = productoRepository.save(existingProducto);

        return Optional.of(mapProductoToResponseDTO(productoActualizado));
    }

    /**
     * Elimina un producto por su ID.
     * @param id ID del producto a eliminar.
     * @return true si el producto fue eliminado.
     * @throws ResourceNotFoundException si el producto no se encuentra.
     */
    @Override
    @Transactional
    public boolean deleteProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto con ID " + id + " no encontrado para eliminar.");
        }
        productoRepository.deleteById(id);
        return true;
    }

    /**
     * Método auxiliar para mapear una entidad Producto a un ProductoResponseDTO.
     * Incluye la lógica para obtener el nombre de la categoría.
     *
     * @param producto La entidad Producto a mapear.
     * @return Un ProductoResponseDTO.
     */
    private ProductoResponseDTO mapProductoToResponseDTO(Producto producto) {
        // Ahora usamos el constructor @AllArgsConstructor de 10 argumentos.
        // El último argumento es nombreCategoria, que se establecerá a continuación.
        ProductoResponseDTO dto = new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getDescripcion(),
                producto.getIdCategoria(),
                producto.getImagen(),
                producto.getFechaCreacion(),
                producto.getFechaActualizacion(),
                null // Inicialmente null para nombreCategoria
        );

        if (producto.getIdCategoria() != null) {
            Optional<Categoria> categoriaOptional = categoriaRepository.findById(producto.getIdCategoria());
            categoriaOptional.ifPresent(categoria -> dto.setNombreCategoria(categoria.getNombre()));
        }
        return dto;
    }
}
