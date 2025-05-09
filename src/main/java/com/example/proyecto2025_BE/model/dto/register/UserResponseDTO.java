package com.example.proyecto2025_BE.model.dto.register;

import com.example.proyecto2025_BE.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDTO {

    private Long id;
    private String fullName;
    private int age;
    private String email;
    private String phoneNumber;



    public static UserResponseDTO fromUser(User user) {

        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName((user.getFullName() == null || user.getFullName().isEmpty()) ? "" : user.getFullName())
                .age(user.getAge() == null ? 0 : user.getAge())
                .email((user.getEmail() == null || user.getEmail().isEmpty()) ? "" : user.getEmail())
                .phoneNumber((user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) ? "" : user.getPhoneNumber())
                .build();
    }


}
