package com.example.proyecto2025_BE.service.pregunta.factory;


import com.example.proyecto2025_BE.configuration.PreguntaProperties;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.service.UserService;
import com.example.proyecto2025_BE.service.pregunta.strategy.InvitadoPreguntaLoader;
import com.example.proyecto2025_BE.service.pregunta.strategy.PreguntaLoaderStrategy;
import com.example.proyecto2025_BE.service.pregunta.strategy.UsuarioRegistradoPreguntaLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class PreguntaLoaderFactory {

    private final PreguntaDao preguntaDao;
    private final PreguntaProperties properties;
    private final UserService userService;

    private final InvitadoPreguntaLoader invitadoLoader = new InvitadoPreguntaLoader();

    private final Map<Long, UsuarioRegistradoPreguntaLoader> usuarioLoaderCache = new ConcurrentHashMap<>();

    public PreguntaLoaderStrategy getPreguntaLoader(Long userId) {
        if (userId == null) {
            return invitadoLoader.init(preguntaDao, properties);
        }

        return usuarioLoaderCache.computeIfAbsent(userId, id ->
                new UsuarioRegistradoPreguntaLoader(preguntaDao, properties, userService, id)
        );
    }
}