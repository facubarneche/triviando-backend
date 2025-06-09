package com.example.proyecto2025_BE.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class PageableConfig implements WebMvcConfigurer {

    /***
     * Configuración de la paginación en los controladores de manera global.
     * De esta forma cualquier endpoint que reciba un Pageable se limita a 20 resultados por página,
     * protegiendo de ataques de fuerza bruta.
      */

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setMaxPageSize(4);
        resolver.setOneIndexedParameters(true);
        resolver.setFallbackPageable(PageRequest.of(0, 10));
        resolvers.add(resolver);
    }
}
