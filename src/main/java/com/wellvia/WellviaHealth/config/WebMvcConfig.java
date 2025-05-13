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
        // Configure resource handler for specialization images using classpath
        registry.addResourceHandler("/resources/static/uploads/specialization/**")
                .addResourceLocations("classpath:/static/uploads/specialization/")
                .setCachePeriod(3600) // Cache for 1 hour
                .resourceChain(true);
    }
} 