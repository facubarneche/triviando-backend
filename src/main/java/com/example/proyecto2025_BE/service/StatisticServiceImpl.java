package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.dao.RespuestasDao;
import com.example.proyecto2025_BE.model.dto.StatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {

    private final RespuestasDao repo;

    @Override
    public StatsResponse getStats(Long userId) {
        var totalQuestions = totalRespondidas(userId);
        var correctAnswers = totalCorrectas(userId);
        var totalQuizzes = totalQuestions / 5;
        return new StatsResponse(totalQuizzes, correctAnswers, totalQuestions);
    }

    public int totalRespondidas(Long usuarioId) {
        return repo.countByUsuarioId(usuarioId);
    }

    public int totalCorrectas(Long usuarioId) {
        return repo.countByUsuarioIdAndCorrectaTrue(usuarioId);
    }
}