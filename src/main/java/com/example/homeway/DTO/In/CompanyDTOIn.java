package com.example.homeway.DTO.In;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTOIn {
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 4, max = 40)
    private String username;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, max = 100)
    private String name;

    @NotEmpty(message = "email must not be empty")
    @Email(message = "email must not be valid")
    private String email;

    @NotEmpty(message = "phone must not be empty")
    private String phone;

    @NotEmpty(message = "country must not be empty")
    private String country;

    @NotEmpty(message = "city must not be empty")
    private String city;

    @NotEmpty(message = "password must not be empty")
    @Size(min = 6, max = 100, message = "password must be more than 5 characters")
    private String password;

    @NotEmpty(message = "role must not be empty")
    private String role;

    private String status;
}
