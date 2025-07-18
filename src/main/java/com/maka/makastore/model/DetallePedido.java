package com.maka.makastore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;          // Importa la anotación @Data de Lombok
import lombok.NoArgsConstructor; // Importa la anotación @NoArgsConstructor de Lombok
import lombok.AllArgsConstructor; // Importa la anotación @AllArgsConstructor de Lombok

/**
 * Clase de entidad que representa un detalle de pedido en la base de datos.
 * Utiliza Lombok para generar automáticamente getters, setters,
 * constructores, toString, equals y hashCode.
 */
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "detalle_pedido") // Mapea esta entidad a la tabla "detalle_pedido"
@Data // Anotación de Lombok que genera automáticamente getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos (necesario para JPA)
@AllArgsConstructor // Genera un constructor con todos los argumentos (útil para inicialización y pruebas)
public class DetallePedido {

    @Id // Marca el campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la estrategia de generación de ID para autoincremento
    @Column(name = "id_detalle_pedido") // Mapea el campo a la columna "id_detalle_pedido"
    private Long idDetallePedido;

    // Si el nombre de la columna en la BD es diferente a 'cantidad', usa @Column(name="nombre_columna_bd")
    private int cantidad;

    @Column(name = "precio_unitario") // Mapea el campo a la columna "precio_unitario"
    private BigDecimal precioUnitario;

    // Si el nombre de la columna en la BD es diferente a 'subtotal', usa @Column(name="nombre_columna_bd")
    private BigDecimal subtotal;

    /**
     * Relación ManyToOne con la entidad Pedido.
     * Un DetallePedido pertenece a un Pedido.
     * @JoinColumn(name = "id_pedido", nullable = false): Especifica la columna FK y que no puede ser nula.
     * @JsonIgnoreProperties: Ignora las propiedades internas de Hibernate para evitar problemas de serialización
     * con carga perezosa y bucles infinitos.
     */
    @ManyToOne(fetch = FetchType.LAZY) // Carga perezosa para el objeto Pedido
    @JoinColumn(name = "id_pedido", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "detallesPedido"}) // Ignora el proxy y evita bucles si Pedido tiene una lista de DetallePedido
    private Pedido pedido;

    /**
     * Relación ManyToOne con la entidad Producto.
     * Un DetallePedido se refiere a un Producto.
     * @JoinColumn(name = "id_producto", nullable = false): Especifica la columna FK y que no puede ser nula.
     * @JsonIgnoreProperties: Ignora las propiedades internas de Hibernate para evitar problemas de serialización
     * con carga perezosa y bucles infinitos.
     */
    @ManyToOne(fetch = FetchType.LAZY) // Carga perezosa para el objeto Producto
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "categoria"}) // Ignora el proxy y la relación 'categoria' de Producto
    private Producto producto;

    // Los Getters y Setters manuales han sido eliminados y serán generados por Lombok (@Data).
}
