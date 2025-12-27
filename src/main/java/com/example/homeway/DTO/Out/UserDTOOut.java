package com.example.homeway.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDTOOut {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String country;
    private String city;
    private LocalDateTime createdAt;
    private String role;
}
