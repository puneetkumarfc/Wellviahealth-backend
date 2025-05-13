package com.wellvia.WellviaHealth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.specialization-resources-url}")
    private String specializationResourcesUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Get the absolute path to the uploads directory
        Path uploadsDir = Paths.get("src/main/resources/static/uploads/specialization");
        String uploadsAbsolutePath = uploadsDir.toFile().getAbsolutePath();

        // Configure resource handler for specialization images
        registry.addResourceHandler("/resources/static/uploads/specialization/**")
                .addResourceLocations("file:" + uploadsAbsolutePath + "/")
                .setCachePeriod(3600) // Cache for 1 hour
                .resourceChain(true);
    }
} 