package com.wellvia.WellviaHealth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.specialization-resources-url}")
    private String specializationResourcesUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure resource handler for specialization images
        registry.addResourceHandler("/resources/static/uploads/specialization/**")
                .addResourceLocations("file:src/main/resources/static/uploads/specialization/");
    }
} 