package com.maka.makastore.dto;

import jakarta.validation.constraints.Email; // Importa la anotación @Email
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size; // Importa la anotación @Size
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO (Data Transfer Object) para recibir datos de creación o actualización de un Cliente.
 * Incluye anotaciones de validación para asegurar la integridad de los datos de entrada.
 */
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido del cliente es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder los 100 caracteres")
    private String apellido;

    @NotBlank(message = "El email del cliente es obligatorio")
    @Email(message = "El email debe tener un formato válido") // Valida el formato del email
    @Size(max = 255, message = "El email no puede exceder los 255 caracteres")
    private String email;

    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    private String telefono;

    @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres")
    private String direccion;
}
