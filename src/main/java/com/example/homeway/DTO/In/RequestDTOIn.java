package com.example.homeway.DTO.In;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTOIn {

    @NotBlank(message = "timeWindow is required")
    private String timeWindow;

    @NotBlank(message = "description is required")
    private String description;

}
