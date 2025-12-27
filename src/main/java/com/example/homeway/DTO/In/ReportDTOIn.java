package com.example.homeway.DTO.In;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTOIn {

    @NotEmpty(message = "Findings is required")
    private String findings;

    @NotEmpty(message = "Recommendations is required")
    private String recommendations;


    @Pattern(regexp = "^(https?://).+", message = "Image URL must start with http:// or https://")
    private String imageURL;
}
