package com.maka.makastore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO (Data Transfer Object) para enviar datos de un Cliente como respuesta de la API.
 * Define la estructura de los datos que el cliente recibirá.
 */
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos (esto es lo que causaba la duplicación)
public class ClienteResponseDTO {

    private Long idCliente;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;

    // El constructor manual ha sido eliminado, ya que @AllArgsConstructor lo genera automáticamente.
    // public ClienteResponseDTO(Long idCliente, String nombre, String apellido,
    //                           String email, String telefono, String direccion) {
    //     this.idCliente = idCliente;
    //     this.nombre = nombre;
    //     this.apellido = apellido;
    //     this.email = email;
    //     this.telefono = telefono;
    //     this.direccion = direccion;
    // }
}
