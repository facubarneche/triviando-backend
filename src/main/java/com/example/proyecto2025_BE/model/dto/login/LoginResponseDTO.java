package com.example.proyecto2025_BE.model.dto.login;

import com.example.proyecto2025_BE.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private Long id;
    private String email;
    private String fullName;


    public static LoginResponseDTO fromEntity(User user) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setId(user.getId());
        loginResponseDTO.setEmail(user.getEmail().isEmpty() ? "" : user.getEmail());
        loginResponseDTO.setFullName(user.getFullName().isEmpty() ? "" : user.getFullName());
        return loginResponseDTO;
    }


}
