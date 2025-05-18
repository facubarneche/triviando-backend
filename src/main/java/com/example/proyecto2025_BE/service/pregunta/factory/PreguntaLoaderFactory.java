package com.example.proyecto2025_BE.service.pregunta.factory;


import com.example.proyecto2025_BE.configuration.PreguntaProperties;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.service.UserService;
import com.example.proyecto2025_BE.service.pregunta.strategy.InvitadoPreguntaLoader;
import com.example.proyecto2025_BE.service.pregunta.strategy.PreguntaLoaderStrategy;
import com.example.proyecto2025_BE.service.pregunta.strategy.UsuarioRegistradoPreguntaLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreguntaLoaderFactory {

    private final PreguntaDao preguntaDao;
    private final PreguntaProperties properties;
    private final UserService userService;

    public PreguntaLoaderStrategy getPreguntaLoader(Long userId) {
        if (userId == null) {
            return InvitadoPreguntaLoader.getInstance(preguntaDao,properties);
        }
        return UsuarioRegistradoPreguntaLoader.getInstance(preguntaDao, properties, userService, userId);
    }
}