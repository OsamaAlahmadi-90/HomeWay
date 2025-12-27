package com.example.homeway.DTO.In;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateDTOIn {
    @NotNull
    private Integer companyId;

    @NotNull
    private Integer propertyId;

    @NotEmpty(message = "timeWindow is required")
    private String timeWindow;

    @NotEmpty(message = "description is required")
    private String description;
}
