package com.maka.makastore.repository;

import com.maka.makastore.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de repositorio para la entidad Cliente.
 * Proporciona operaciones CRUD básicas para la tabla 'clientes'.
 */
@Repository // Indica que esta interfaz es un componente de repositorio de Spring
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Spring Data JPA generará automáticamente la implementación de los métodos CRUD
    // (save, findById, findAll, deleteById, etc.)
}
