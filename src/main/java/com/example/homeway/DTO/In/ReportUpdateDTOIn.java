package com.example.homeway.DTO.In;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportUpdateDTOIn {

    private String findings;

    private String recommendations;

    @Pattern(regexp = "^(https?://).+", message = "Image URL must start with http:// or https://")
    private String imageURL;
}
