package com.example.proyecto2025_BE.utils.ranking;

import java.math.BigDecimal;


public interface UserRankingProjection {
    Long getId();
    String getUserName();
    BigDecimal getScore();
    Integer getPosition();
}

