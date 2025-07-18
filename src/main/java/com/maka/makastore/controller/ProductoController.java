package com.maka.makastore.controller;

import com.maka.makastore.dto.ProductoRequestDTO;
import com.maka.makastore.dto.ProductoResponseDTO;
import com.maka.makastore.service.ProductoService; // Importa el servicio de productos

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Se eliminan las importaciones de MethodArgumentNotValidException, ExceptionHandler y ResponseStatus
// ya que el manejo de excepciones ahora es global.

import java.util.List;
// Se elimina la importación de Collectors ya que ya no se usa directamente para el manejo de excepciones.

/**
 * Controlador REST para la gestión de productos.
 * Delega la lógica de negocio a la capa de servicio (ProductoService).
 * El manejo de excepciones de validación se realiza globalmente a través de GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService; // Ahora inyectamos el servicio

    // Inyección de dependencias a través del constructor (preferido)
    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Se ha eliminado el bloque @ExceptionHandler local para MethodArgumentNotValidException.
    // Este tipo de excepción ahora será manejado por GlobalExceptionHandler.

    /**
     * Crea un nuevo producto utilizando un DTO de petición.
     * Delega la creación al servicio.
     *
     * @param productoRequestDTO El DTO con los datos del producto a crear.
     * @return ResponseEntity con el DTO del producto creado y estado HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> createProducto(@Valid @RequestBody ProductoRequestDTO productoRequestDTO) {
        // La lógica de mapeo y guardado se ha movido al servicio
        ProductoResponseDTO nuevoProductoDTO = productoService.createProducto(productoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProductoDTO);
    }

    /**
     * Obtiene la lista de todos los productos y los devuelve como DTOs de respuesta.
     * Delega la obtención al servicio.
     *
     * @return ResponseEntity con la lista de DTOs de productos y estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> getAllProductos() {
        // La lógica de obtención y mapeo se ha movido al servicio
        List<ProductoResponseDTO> responseDTOs = productoService.getAllProductos();
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Obtiene un producto por su ID y lo devuelve como un DTO de respuesta.
     * Delega la obtención al servicio.
     *
     * @param id El ID del producto.
     * @return ResponseEntity con el DTO del producto encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (Not Found) si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> getProductoById(@PathVariable Long id) {
        // La lógica de obtención y mapeo se ha movido al servicio
        return productoService.getProductoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualiza un producto existente utilizando un DTO de petición.
     * Delega la actualización al servicio.
     *
     * @param id El ID del producto a actualizar.
     * @param productoRequestDTO El DTO con los datos actualizados del producto.
     * @return ResponseEntity con el DTO del producto actualizado y estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO productoRequestDTO) {
        // La lógica de actualización y mapeo se ha movido al servicio
        return productoService.updateProducto(id, productoRequestDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Elimina un producto por su ID.
     * Delega la eliminación al servicio.
     *
     * @param id El ID del producto a eliminar.
     * @return ResponseEntity con estado HTTP 204 (No Content) si la eliminación es exitosa,
     * o estado HTTP 404 (Not Found) si el producto no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        // La lógica de eliminación se ha movido al servicio
        boolean deleted = productoService.deleteProducto(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
