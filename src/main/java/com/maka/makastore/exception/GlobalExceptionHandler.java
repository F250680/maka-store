package com.maka.makastore.exception;

import org.springframework.dao.DataIntegrityViolationException; // Importa la excepción de integridad de datos
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase de manejo global de excepciones para la aplicación REST.
 * Captura excepciones lanzadas por los controladores y servicios,
 * y devuelve respuestas de error estandarizadas.
 */
@ControllerAdvice // Indica que esta clase es un componente que maneja excepciones globalmente
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de validación de argumentos de métodos (ej. @Valid en DTOs).
     * Devuelve un mapa de errores de validación con el estado HTTP 400 (Bad Request).
     *
     * @param ex La excepción MethodArgumentNotValidException.
     * @return ResponseEntity con un mapa de errores y HttpStatus.BAD_REQUEST.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Establece el código de estado HTTP 400
    @ExceptionHandler(MethodArgumentNotValidException.class) // Captura esta excepción específica
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Maneja la excepción personalizada ResourceNotFoundException.
     * Devuelve un mensaje de error con el estado HTTP 404 (Not Found).
     *
     * @param ex La excepción ResourceNotFoundException.
     * @return ResponseEntity con el mensaje de error y HttpStatus.NOT_FOUND.
     */
    // La anotación @ResponseStatus ya está en ResourceNotFoundException,
    // pero podemos definir un cuerpo de respuesta aquí si queremos.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Maneja las excepciones de violación de integridad de datos (ej. email único duplicado).
     * Devuelve un mensaje de error con el estado HTTP 409 (Conflict).
     *
     * @param ex La excepción DataIntegrityViolationException.
     * @return ResponseEntity con un mensaje de error y HttpStatus.CONFLICT.
     */
    @ResponseStatus(HttpStatus.CONFLICT) // Establece el código de estado HTTP 409
    @ExceptionHandler(DataIntegrityViolationException.class) // Captura esta excepción específica
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Puedes personalizar el mensaje para que sea más amigable al usuario,
        // por ejemplo, extrayendo el detalle de la causa raíz.
        String errorMessage = "Error de integridad de datos: " + ex.getMostSpecificCause().getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    /**
     * Maneja cualquier otra excepción no capturada por manejadores más específicos.
     * Devuelve un mensaje de error genérico con el estado HTTP 500 (Internal Server Error).
     *
     * @param ex La excepción genérica.
     * @return ResponseEntity con un mensaje de error y HttpStatus.INTERNAL_SERVER_ERROR.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Establece el código de estado HTTP 500
    @ExceptionHandler(Exception.class) // Captura cualquier otra excepción
    public ResponseEntity<String> handleAllUncaughtException(Exception ex) {
        // En un entorno de producción, podrías querer registrar el error completo
        // y devolver un mensaje más genérico al cliente por seguridad.
        return ResponseEntity.internalServerError().body("Ocurrió un error inesperado: " + ex.getMessage());
    }
}
