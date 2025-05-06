package com.example.proyecto2025_BE.unittests;

import com.example.proyecto2025_BE.dao.RespuestasDao;
import com.example.proyecto2025_BE.model.dto.StatsResponse;
import com.example.proyecto2025_BE.service.StatisticServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {

    @Mock
    private RespuestasDao respuestasDao;

    @InjectMocks
    private StatisticServiceImpl estadisticasService;

    @Test
    void totalRespondidas_deberiaRetornarCantidadEsperada() {
        when(respuestasDao.countByUsuarioId(anyLong())).thenReturn(10);
        var total = estadisticasService.totalRespondidas(anyLong());
        assertEquals(10L, total);
    }

    @Test
    void totalCorrectas_deberiaRetornarCantidadEsperada() {
        when(respuestasDao.countByUsuarioIdAndCorrectaTrue(anyLong())).thenReturn(6);
        var correctas = estadisticasService.totalCorrectas(anyLong());
        assertEquals(6L, correctas);
    }

    @Test
    void getStats_deberiaRetornarStatsResponseCorrecta() {
        when(respuestasDao.countByUsuarioId(anyLong())).thenReturn(20);
        when(respuestasDao.countByUsuarioIdAndCorrectaTrue(anyLong())).thenReturn(5);
        var statsResponse = new StatsResponse(4, 5, 20);
        assertEquals(statsResponse, estadisticasService.getStats(anyLong()));
    }

    @Test
    void getStats_sinRespuestasDeberiaRetornarCeros() {
        when(respuestasDao.countByUsuarioId(anyLong())).thenReturn(0);
        when(respuestasDao.countByUsuarioIdAndCorrectaTrue(anyLong())).thenReturn(0);
        var statsResponse = new StatsResponse(0,0,0);
        assertEquals(statsResponse, estadisticasService.getStats(anyLong()));
    }
}