package com.maka.makastore.config; // Paquete correcto

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica la configuración a todos los endpoints de la API
                // Orígenes permitidos:
                // - Para desarrollo local con React
                // - ¡IMPORTANTE! Para producción, reemplaza o añade la URL de tu frontend desplegado
                .allowedOrigins("http://localhost:3000" /*, "https://tudominio.com" */)
                // Métodos HTTP permitidos:
                // En desarrollo, "*" es conveniente. En producción, es más seguro especificar solo los que usas.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos CRUD y OPTIONS para pre-vuelo CORS
                // Encabezados permitidos:
                // "*" permite todos los encabezados. Para mayor seguridad en producción, especifica los necesarios
                // (ej. "Authorization", "Content-Type", "X-Requested-With").
                .allowedHeaders("*")
                // Permite el envío de credenciales como cookies o encabezados de autorización.
                // Necesario si tu frontend envía tokens de autenticación o cookies.
                .allowCredentials(true)
                // Tiempo máximo en segundos que el navegador puede cachear los resultados de la solicitud pre-vuelo OPTIONS.
                // Reduce el número de solicitudes pre-vuelo.
                .maxAge(3600); // 1 hora
    }
}
