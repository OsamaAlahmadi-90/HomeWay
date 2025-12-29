package com.example.homeway.DTO.Ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionDTOIn {

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1500, message = "Description must be between 10 and 1500 characters")
    private String description;
}