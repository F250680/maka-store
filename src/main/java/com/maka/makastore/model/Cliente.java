package com.maka.makastore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor; // Importa la anotación @NoArgsConstructor de Lombok
import lombok.AllArgsConstructor; // Importa la anotación @AllArgsConstructor de Lombok

/**
 * Clase de entidad que representa un cliente en la base de datos.
 * Utiliza Lombok para generar automáticamente getters, setters,
 * constructores, toString, equals y hashCode.
 */
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "clientes") // Mapea esta entidad a la tabla "clientes" en la base de datos
@Data // Anotación de Lombok que genera automáticamente getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos (necesario para JPA)
@AllArgsConstructor // Genera un constructor con todos los argumentos (útil para inicialización y pruebas)
public class Cliente {

    @Id // Marca el campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la estrategia de generación de ID para autoincremento
    @Column(name = "id_cliente") // Mapea el campo a la columna "id_cliente"
    private Long idCliente;

    @Column(nullable = false) // Indica que la columna 'nombre' no puede ser nula
    private String nombre;

    @Column(nullable = false) // Indica que la columna 'apellido' no puede ser nula
    private String apellido;

    @Column(nullable = false, unique = true) // 'email' no nulo y debe ser único
    private String email;

    private String telefono; // Mapea a una columna 'telefono' por defecto
    private String direccion; // Mapea a una columna 'direccion' por defecto

    // Opcional: Relación OneToMany con Pedido
    // Si quieres que un cliente "conozca" los pedidos que ha realizado.
    // Cuidado con la serialización (ver nota abajo).
    // import java.util.List;
    // import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    // @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cliente"}) // Para evitar bucles infinitos de serialización y Lazy Loading
    // private List<Pedido> pedidos;

    // Con @Data de Lombok, no necesitas escribir manualmente los getters y setters.
    // Lombok los generará en tiempo de compilación.
}
