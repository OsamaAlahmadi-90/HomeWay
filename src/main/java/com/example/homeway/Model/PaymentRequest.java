package com.example.homeway.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    @Pattern(regexp = "\\d{13,19}")
    private String number;

    @NotEmpty
    @Pattern(regexp = "\\d{3,4}")
    private String cvc;

    @NotEmpty
    @Pattern(regexp = "^(0?[1-9]|1[0-2])$")
    private String month;

    @NotEmpty
    @Pattern(regexp = "^\\d{2,4}$")
    private String year;

    @NotEmpty
    private String currency; // "SAR"

    @Size(max = 255)
    private String description;

    @NotEmpty
    private String callbackUrl;



}
