package com.example.homeway.DTO.In;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTOIn {

    @NotEmpty
    @Size(min = 4, max = 40)
    private String username;

    @NotEmpty
    @Size(min = 3, max = 100)
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

    @NotEmpty
    @Size(min = 6, max = 100)
    private String password;
}

