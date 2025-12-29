package com.example.homeway.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistorySubscription {
    private Double price;
    private LocalDateTime paidAt;
    private String isPaid;
}
