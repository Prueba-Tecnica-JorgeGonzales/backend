package com.test.backend.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@EnableWebMvc
public class OpenApiConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200", "https://ayni-web-app.netlify.app")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    // 2. CONFIGURACIÓN VISUAL DE SWAGGER
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Usuarios y Seguridad") // Título Principal
                        .version("1.0.0") // Versión de tu API
                        .description("Esta API permite la gestión centralizada de usuarios, " +
                                "incluyendo autenticación (Login/Registro), mantenimiento CRUD " +
                                "y seguridad mediante hashing de contraseñas.\n\n" +
                                "**Tecnologías:** Spring Boot 3, Spring Security, MySQL, JPA.") // Descripción larga (acepta Markdown)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact()
                                .name("Soporte Técnico")
                                .url("https://tu-web.com")
                                .email("soporte@tu-empresa.com")));
    }
}



