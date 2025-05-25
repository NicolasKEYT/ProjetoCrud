package com.okr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OkrApplication {

    public static void main(String[] args) {
        SpringApplication.run(OkrApplication.class, args);
    }

    /**
     * Configurações de CORS globais.
     * <p>
     * Aqui liberamos GET, POST, PUT, DELETE e OPTIONS para qualquer origem.
     * Em produção, troque `allowedOrigins("*")` pela URL exata do seu front.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                  .addMapping("/**")
                  .allowedOrigins("*")
                  .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
