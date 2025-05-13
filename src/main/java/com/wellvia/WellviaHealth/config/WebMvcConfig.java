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
        // Configure resource handler for external images
        registry.addResourceHandler("/resources/external_img/**")
                .addResourceLocations("file:/home/ec2-user/java code/resources/external_img/")
                .setCachePeriod(3600) // Cache for 1 hour
                .resourceChain(true);
    }
} 