package com.example.homeway.DTO.Out;

import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscriptionDTOOut {
    private String planType;
    private String status;
    private Double monthlyPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime nextBillingDate;
}
