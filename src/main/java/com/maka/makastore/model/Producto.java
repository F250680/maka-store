package com.maka.makastore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore; // Importar JsonIgnore

/**
 * Clase de entidad que representa un producto en la base de datos.
 * Utiliza Lombok para generar automáticamente getters, setters,
 * constructores, toString, equals y hashCode.
 */
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = true)
    private Integer stock;

    private String descripcion;

    @Column(name = "id_categoria")
    private Long idCategoria;

    private String imagen;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    /**
     * Relación ManyToOne con la entidad Categoria.
     * Un Producto pertenece a una Categoria.
     * fetch = FetchType.LAZY: Carga la categoría solo cuando se accede a ella (mejora el rendimiento).
     * @JoinColumn: Especifica la columna de unión.
     * - name = "id_categoria": La columna en la tabla 'productos' que es la clave foránea.
     * - insertable = false, updatable = false: Indica que JPA no debe intentar insertar o actualizar
     * esta columna a través de esta relación, ya que la estamos manejando manualmente
     * con el campo 'idCategoria' de tipo Long. Esto evita conflictos.
     *
     * @JsonIgnore: Importante para evitar problemas de serialización con Jackson
     * cuando la relación es LAZY y la categoría no ha sido cargada.
     * También previene bucles infinitos de serialización si Categoria tuviera una lista de Productos.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    @JsonIgnore // Ignora este campo durante la serialización JSON
    private Categoria categoria;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
