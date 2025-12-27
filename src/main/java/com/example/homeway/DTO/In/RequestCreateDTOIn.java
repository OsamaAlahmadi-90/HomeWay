package com.example.homeway.DTO.In;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateDTOIn {

    @NotBlank(message = "timeWindow is required")
    private String timeWindow;

    @NotBlank(message = "description is required")
    private String description;
}
