package com.wellvia.WellviaHealth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.wellvia.WellviaHealth.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.disable()) // Disable Spring Security's CORS
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow OPTIONS requests
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Error endpoint
                .requestMatchers("/error").permitAll()
                
                // Actuator endpoints
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/actuator/health/**").permitAll()
                .requestMatchers("/actuator/prometheus").permitAll()
                .requestMatchers("/actuator/info").permitAll()
                .requestMatchers("/actuator/metrics/**").permitAll()
                .requestMatchers("/actuator/**").hasRole("ADMIN")
                
                // Public endpoints
                .requestMatchers("/api/auth/**").permitAll()
                
                // Role-based endpoints
                .requestMatchers("/api/doctor/**").hasRole("DOCTOR")
                .requestMatchers("/api/patient/**").hasRole("PATIENT")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/laboratories/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form.disable());

        return http.build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));  // Allow all origins
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));  // Allow all headers
//        configuration.setExposedHeaders(Arrays.asList("*"));  // Expose all headers
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}