package com.example.proyecto2025_BE.utils;

import java.math.BigDecimal;


public interface UserRankingProjection {
    Long getId();
    String getUserName();
    BigDecimal getScore();
    Integer getPosition();
}