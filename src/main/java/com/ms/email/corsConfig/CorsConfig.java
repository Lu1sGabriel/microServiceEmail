package com.ms.email.corsConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/emails/sendingEmail")
                .allowedOrigins("http://127.0.0.1:5500") // Substitua pelo domínio da sua página
                .allowedMethods("POST") // Método permitido
                .allowedHeaders("*"); // Cabeçalhos permitidos
    }
}