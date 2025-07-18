package com.maka.makastore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada para indicar que un recurso solicitado no fue encontrado.
 * Cuando esta excepción es lanzada, Spring responderá con un estado HTTP 404 Not Found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // Esta anotación hace que Spring devuelva un 404 Not Found automáticamente
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor que acepta un mensaje detallado para la excepción.
     * @param message El mensaje que describe la causa de la excepción.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor que acepta un mensaje y una causa raíz para la excepción.
     * @param message El mensaje que describe la causa de la excepción.
     * @param cause La causa raíz de la excepción.
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
