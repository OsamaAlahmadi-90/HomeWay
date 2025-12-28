package com.example.homeway.DTO.In;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferUpdateDTOIn {

    @Min(value = 0, message = "Price must be 0 or more")
    private Double price;

    @Pattern(regexp = "^(Pending|Accepted|Rejected)$", message = "Status must be either 'paid' or 'not_paid'")
    private String status;
}
