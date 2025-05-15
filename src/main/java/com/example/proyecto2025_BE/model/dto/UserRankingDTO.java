package com.example.proyecto2025_BE.model.dto;

import com.example.proyecto2025_BE.model.User;

import java.math.BigDecimal;

public record UserRankingDTO(Long id, String fullName, BigDecimal score,int position) {

    public  static UserRankingDTO fromUser(User user,int position) {
        return new UserRankingDTO(user.getId(), user.getUsername(), user.getScore(), position);
    }
}
