package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.model.dto.StatsResponse;

public interface StatisticService {
    StatsResponse getStats(Long userId);
}
