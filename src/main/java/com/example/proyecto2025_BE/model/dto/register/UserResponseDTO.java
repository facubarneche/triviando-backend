package com.example.proyecto2025_BE.model.dto.register;

import com.example.proyecto2025_BE.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String fullName;
    private int age;
    private String email;
    private String phoneNumber;


    public static UserResponseDTO fromUser(User user) {
        UserResponseDTO userDTOResponse = new UserResponseDTO();
        userDTOResponse.setId(user.getId());
        userDTOResponse.setFullName((user.getFullName() == null || user.getFullName().isEmpty()) ? "" : user.getFullName());
        userDTOResponse.setAge(user.getAge() == null ? 0 : user.getAge());
        userDTOResponse.setEmail((user.getEmail() == null || user.getEmail().isEmpty()) ? "" : user.getEmail());
        userDTOResponse.setPhoneNumber((user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) ? "" : user.getPhoneNumber());
        return userDTOResponse;
    }


}
