package com.example.homeway.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyDTOOut {
    private Integer id;
    private String status;

    //user
    private String name;
    private String email;
    private String phone;
    private String country;
    private String city;
    private String role;
}
