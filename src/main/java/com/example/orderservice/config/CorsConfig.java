package com.example.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins(
                        "https://order-management-frontend.vercel.app",
                        "https://order-management-frontend-99mb-gslr5wryr-shaw0ps-projects.vercel.app",
                        "https://order-management-frontend-99mb-m4ghoi67z-shaw0ps-projects.vercel.app",
                        "https://order-management-frontend-99mb-4snwo3h69-shaw0ps-projects.vercel.app",
                        "https://order-management-frontend-99mb-mf4dv5ha9-shaw0ps-projects.vercel.app"
                    )
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") 
                    .allowedHeaders("*")
                    .allowCredentials(true); 
            }
        };
    }
}
