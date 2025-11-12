package com.example.onlineCourses.mapper;

import com.example.onlineCourses.DTO.UserDTO;
import com.example.onlineCourses.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}

