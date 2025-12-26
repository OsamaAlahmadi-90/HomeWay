package com.example.homeway.DTO.In;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropertyDTOIn {

    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "Nickname is required")
    private String nickname;

    @NotEmpty(message = "Type is required")
    private String type;
}
