package com.example.homeway.DTO.Ai;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAssistDTOIn {

    @NotEmpty(message = "Notes are required")
    private String notes;

    @NotEmpty(message = "Tone is required (polite/strict/neutral)")
    private String tone;
}