package com.example.social.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    private static final String[] ALLOWED_METHODS = {
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"
    };

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // First CORS configuration
//        CorsConfiguration config1 = new CorsConfiguration();
//        config1.setAllowedOrigins(Arrays.asList(
//                "https://*.expertbox.io",
//                "http://*.localhost:9000",
//                "http://127.0.0.1:9000",
//                "http://bs-local.com"
//        ));
//        config1.setAllowedMethods(Arrays.asList(ALLOWED_METHODS));
//        config1.setAllowedHeaders(Arrays.asList("*"));
//        config1.addExposedHeader("Content-Disposition");
//        source.registerCorsConfiguration("/**", config1);

        // Second CORS configuration for specific endpoint
        CorsConfiguration config2 = new CorsConfiguration();
        config2.addAllowedOrigin("*");
        config2.setAllowedMethods(Arrays.asList(ALLOWED_METHODS));
        config2.setAllowedHeaders(Arrays.asList("*"));
        source.registerCorsConfiguration("/api/v1/workspaces/*/public/widgets/*", config2);

        return new CorsFilter(source);

    }
}