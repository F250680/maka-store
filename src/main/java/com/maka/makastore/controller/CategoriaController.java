package com.maka.makastore.controller;

import com.maka.makastore.dto.CategoriaRequestDTO; // Importa el DTO de petición
import com.maka.makastore.dto.CategoriaResponseDTO; // Importa el DTO de respuesta
import com.maka.makastore.service.CategoriaService; // Importa el nuevo servicio de categorías

import jakarta.validation.Valid; // Importa la anotación @Valid
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de categorías.
 * Delega la lógica de negocio a la capa de servicio (CategoriaService).
 * El manejo de excepciones de validación y recursos no encontrados se realiza
 * globalmente a través de GlobalExceptionHandler.
 */
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/categorias") // Define la ruta base para todos los endpoints de este controlador
@CrossOrigin(origins = "http://localhost:3000") // Permite peticiones desde el frontend en localhost:3000
public class CategoriaController {

    private final CategoriaService categoriaService; // Inyectamos el servicio de categorías

    // Inyección de dependencias a través del constructor (preferido)
    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Crea una nueva categoría utilizando un DTO de petición.
     * Delega la creación al servicio.
     *
     * @param categoriaRequestDTO El DTO con los datos de la categoría a crear.
     * @return ResponseEntity con el DTO de la categoría creada y estado HTTP 201 (Created).
     */
    @PostMapping // Mapea las peticiones POST a /api/categorias
    public ResponseEntity<CategoriaResponseDTO> createCategoria(@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        CategoriaResponseDTO nuevaCategoriaDTO = categoriaService.createCategoria(categoriaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoriaDTO);
    }

    /**
     * Obtiene la lista de todas las categorías y las devuelve como DTOs de respuesta.
     * Delega la obtención al servicio.
     *
     * @return ResponseEntity con la lista de DTOs de categorías y estado HTTP 200 (OK).
     */
    @GetMapping // Mapea las peticiones GET a /api/categorias
    public ResponseEntity<List<CategoriaResponseDTO>> getAllCategorias() {
        List<CategoriaResponseDTO> responseDTOs = categoriaService.getAllCategorias();
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Obtiene una categoría por su ID y la devuelve como un DTO de respuesta.
     * Delega la obtención al servicio.
     *
     * @param id ID de la categoría.
     * @return ResponseEntity con el DTO de la categoría encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (Not Found) si no se encuentra (manejado por GlobalExceptionHandler).
     */
    @GetMapping("/{id}") // Mapea las peticiones GET a /api/categorias/{id}
    public ResponseEntity<CategoriaResponseDTO> getCategoriaById(@PathVariable Long id) {
        // El servicio lanzará ResourceNotFoundException si no se encuentra,
        // que será capturada por GlobalExceptionHandler para devolver un 404.
        return categoriaService.getCategoriaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // Aunque el servicio ya lanza, esto es un fallback
    }

    /**
     * Actualiza una categoría existente utilizando un DTO de petición.
     * Delega la actualización al servicio.
     *
     * @param id ID de la categoría a actualizar.
     * @param categoriaRequestDTO DTO con los datos actualizados de la categoría.
     * @return ResponseEntity con el DTO de la categoría actualizada y estado HTTP 200 (OK).
     */
    @PutMapping("/{id}") // Mapea las peticiones PUT a /api/categorias/{id}
    public ResponseEntity<CategoriaResponseDTO> updateCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        // El servicio lanzará ResourceNotFoundException si no se encuentra,
        // que será capturada por GlobalExceptionHandler para devolver un 404.
        return categoriaService.updateCategoria(id, categoriaRequestDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // Aunque el servicio ya lanza, esto es un fallback
    }

    /**
     * Elimina una categoría por su ID.
     * Delega la eliminación al servicio.
     *
     * @param id ID de la categoría a eliminar.
     * @return ResponseEntity con estado HTTP 204 (No Content) si la eliminación es exitosa,
     * o estado HTTP 404 (Not Found) si la categoría no existe (manejado por GlobalExceptionHandler).
     */
    @DeleteMapping("/{id}") // Mapea las peticiones DELETE a /api/categorias/{id}
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        // El servicio lanzará ResourceNotFoundException si no se encuentra,
        // que será capturada por GlobalExceptionHandler para devolver un 404.
        boolean deleted = categoriaService.deleteCategoria(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); // Aunque el servicio ya lanza, esto es un fallback
        }
    }
}
