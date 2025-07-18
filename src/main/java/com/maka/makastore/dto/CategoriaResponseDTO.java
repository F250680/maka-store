package com.maka.makastore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO (Data Transfer Object) para enviar datos de una Categoría como respuesta de la API.
 * Define la estructura de los datos que el cliente recibirá.
 */
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all arguments (this is what caused the duplication)
public class CategoriaResponseDTO {

    private Long idCategoria;
    private String nombre;
    private String descripcion;

    // El constructor manual ha sido eliminado, ya que @AllArgsConstructor lo genera automáticamente.
    // public CategoriaResponseDTO(Long idCategoria, String nombre, String descripcion) {
    //     this.idCategoria = idCategoria;
    //     this.nombre = nombre;
    //     this.descripcion = descripcion;
    // }
}
