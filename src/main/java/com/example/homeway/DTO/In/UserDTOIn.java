package com.example.homeway.DTO.In;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOIn {

    @NotEmpty
    private String username;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String country;

    @NotEmpty
    private String city;

    //i might remove this
    private String password;

    @NotEmpty
    private String role;
}
