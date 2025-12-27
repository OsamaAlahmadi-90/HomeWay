package com.example.homeway.DTO.In;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyStatusDTOIn {

    @NotEmpty
    private String status; // pending / approved / rejected
}
