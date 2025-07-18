package com.maka.makastore.model;

import jakarta.persistence.*;
import lombok.Data;          // Importar Data de Lombok
import lombok.NoArgsConstructor; // Opcional, si necesitas un constructor sin argumentos
import lombok.AllArgsConstructor; // Opcional, si necesitas un constructor con todos los argumentos

/**
 * Clase de entidad que representa una categoría de producto en la base de datos.
 * Utiliza Lombok para generar automáticamente getters, setters,
 * constructores, toString, equals y hashCode.
 */
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "categorias") // Mapea esta entidad a la tabla "categorias" en la base de datos
@Data // Anotación de Lombok que genera automáticamente:
// - Getters para todos los campos
// - Setters para todos los campos
// - Constructor @NoArgsConstructor (si no hay otro constructor explícito)
// - toString(), equals(), y hashCode()
@NoArgsConstructor // Genera un constructor sin argumentos (necesario para JPA)
@AllArgsConstructor // Genera un constructor con todos los argumentos (útil para inicialización y pruebas)
public class Categoria {

    @Id // Marca el campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la estrategia de generación de ID
    @Column(name = "id_categoria") // Mapea el campo a la columna "id_categoria"
    private Long idCategoria;

    @Column(nullable = false, unique = true) // El nombre de la categoría no puede ser nulo y debe ser único
    private String nombre;

    private String descripcion; // Mapea a una columna 'descripcion' por defecto

    // Opcional: Relación OneToMany con Producto
    // Si quieres que una categoría "conozca" los productos que le pertenecen.
    // Cuidado con la serialización (ver nota abajo).
    // @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JsonIgnore // Para evitar bucles infinitos de serialización y problemas de Lazy Loading
    // private List<Producto> productos;

    // Con @Data de Lombok, no necesitas escribir manualmente los getters y setters.
    // Lombok los generará en tiempo de compilación.
}