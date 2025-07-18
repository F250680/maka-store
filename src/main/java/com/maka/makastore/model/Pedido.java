package com.maka.makastore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List; // Necesario para la lista de detalles de pedido
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Necesario para evitar bucles de serialización

// Lombok annotations (assuming you'll add them)
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Clase de entidad que representa un pedido en la base de datos.
 * Utiliza Lombok para generar automáticamente getters, setters,
 * constructores, toString, equals y hashCode.
 */
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "pedidos") // Mapea esta entidad a la tabla "pedidos" en la base de datos
@Data // Anotación de Lombok que genera automáticamente getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos (necesario para JPA)
@AllArgsConstructor // Genera un constructor con todos los argumentos (útil para inicialización y pruebas)
public class Pedido {

    @Id // Marca el campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la estrategia de generación de ID para autoincremento
    @Column(name = "id_pedido") // Mapea el campo a la columna "id_pedido"
    private Long idPedido;

    @Column(name = "fecha_pedido", nullable = false) // Mapea a 'fecha_pedido', no nulo
    private LocalDateTime fechaPedido;

    @Column(nullable = false, precision = 10, scale = 2) // 'total' no nulo, con 10 dígitos en total y 2 decimales
    private BigDecimal total;

    @Column(name = "estado_pedido") // Mapea a 'estado_pedido'
    private String estadoPedido;

    @Column(name = "id_cliente", nullable = false) // Mapea a 'id_cliente', no nulo
    private Long idCliente; // Campo para manejar directamente la FK del cliente

    /**
     * Relación ManyToOne con la entidad Cliente.
     * Un Pedido pertenece a un Cliente.
     * fetch = FetchType.LAZY: Carga perezosa para el objeto Cliente.
     * @JoinColumn(name = "id_cliente", insertable = false, updatable = false):
     * Indica que la columna 'id_cliente' es la FK y que JPA no debe intentar
     * insertarla/actualizarla a través de esta relación, ya que la manejamos
     * manualmente con el campo 'idCliente'.
     * @JsonIgnoreProperties: Ignora las propiedades internas de Hibernate para evitar problemas
     * de serialización con carga perezosa y bucles infinitos si Cliente tiene una lista de Pedidos.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "pedidos"}) // Ignora el proxy y evita bucles si Cliente tiene una lista de Pedidos
    private Cliente cliente;

    /**
     * Relación OneToMany con la entidad DetallePedido.
     * Un Pedido puede tener muchos DetallePedido.
     * mappedBy = "pedido": Indica que el campo 'pedido' en la entidad DetallePedido es el dueño de la relación.
     * cascade = CascadeType.ALL: Las operaciones (persist, merge, remove, refresh, detach) se propagan a los detalles.
     * fetch = FetchType.LAZY: Carga perezosa para la lista de detalles de pedido.
     * @JsonIgnoreProperties: Ignora las propiedades internas de Hibernate y el campo 'pedido' en DetallePedido
     * para evitar bucles infinitos de serialización.
     */
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "pedido"}) // Ignora el proxy y el campo 'pedido' en DetallePedido
    private List<DetallePedido> detallesPedido;

    // Con @Data de Lombok, no necesitas escribir manualmente los getters y setters.
    // Lombok los generará en tiempo de compilación.

    /**
     * Método que se ejecuta automáticamente antes de que la entidad sea persistida (guardada por primera vez).
     * Establece la fecha de pedido.
     */
    @PrePersist
    protected void onCreate() {
        this.fechaPedido = LocalDateTime.now();
    }
}
