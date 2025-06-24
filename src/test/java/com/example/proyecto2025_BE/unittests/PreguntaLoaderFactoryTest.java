package com.example.proyecto2025_BE.unittests;


import com.example.proyecto2025_BE.configuration.PreguntaProperties;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.service.UserService;
import com.example.proyecto2025_BE.service.pregunta.factory.PreguntaLoaderFactory;
import com.example.proyecto2025_BE.service.pregunta.strategy.InvitadoPreguntaLoader;
import com.example.proyecto2025_BE.service.pregunta.strategy.PreguntaLoaderStrategy;
import com.example.proyecto2025_BE.service.pregunta.strategy.UsuarioRegistradoPreguntaLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PreguntaLoaderFactoryTest {

    @Mock
    private PreguntaDao preguntaDao;

    @Mock
    private PreguntaProperties properties;

    @Mock
    private UserService userService;

    @InjectMocks
    private PreguntaLoaderFactory preguntaLoaderFactory;

    @Test
    void getPreguntaLoaderConUsuarioNuloTest() {
        // Arrange
        Long userId = null;

        // Act
        PreguntaLoaderStrategy result = preguntaLoaderFactory.getPreguntaLoader(userId);

        // Assert
        assertInstanceOf(InvitadoPreguntaLoader.class, result);
    }

    @Test
    void getPreguntaLoaderConUsuarioValidoTest() {
        // Arrange
        Long userId = 1L;

        // Act
        PreguntaLoaderStrategy result = preguntaLoaderFactory.getPreguntaLoader(userId);

        // Assert
        assertInstanceOf(UsuarioRegistradoPreguntaLoader.class, result);
    }

    @Test
    void getPreguntaLoaderReutilizaLoaderParaMismoUsuarioTest() {
        // Arrange
        Long userId = 1L;

        // Act
        PreguntaLoaderStrategy result1 = preguntaLoaderFactory.getPreguntaLoader(userId);
        PreguntaLoaderStrategy result2 = preguntaLoaderFactory.getPreguntaLoader(userId);

        // Assert
        assertSame(result1, result2); // Verifica que es la misma instancia
    }
}
