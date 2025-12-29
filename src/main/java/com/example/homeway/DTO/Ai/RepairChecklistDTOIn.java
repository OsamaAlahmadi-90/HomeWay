package com.example.homeway.DTO.Ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairChecklistDTOIn {

    @NotBlank(message = "Issue description is required")
    @Size(min = 10, max = 1000, message = "Issue description must be between 10 and 1000 characters")
    private String issueDescription;
}
