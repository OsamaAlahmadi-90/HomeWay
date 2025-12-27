package com.example.homeway.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestDTOOut {
    private Integer id;
    private String status;
    private String type;

    private LocalDateTime createdAt;
    private LocalDate startDate;
    private LocalDate endDate;

    private String timeWindow;
    private String description;
    private Boolean isPaid;

    private Integer customerId;
    private Integer companyId;
    private Integer propertyId;

    private Integer workerId;
    private Integer vehicleId;
    private Integer offerId;
}
