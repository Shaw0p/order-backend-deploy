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
                        "https://order-management-frontend-99mb-ouww0jk0u-shaw0ps-projects.vercel.app",
                        "https://order-management-frontend-99mb-1oowgueao-shaw0ps-projects.vercel.app",
                        "https://order-management-frontend-99mb-442fm1y8a-shaw0ps-projects.vercel.app"
                    )
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*");
            }
        };
    }
}
