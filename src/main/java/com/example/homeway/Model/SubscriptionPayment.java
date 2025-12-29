package com.example.homeway.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubscriptionPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(100)")
    private String name;

    @Column(columnDefinition = "varchar(20)")
    private String number;

    @Column(columnDefinition = "varchar(10)")
    private String cvc;

    @Column(columnDefinition = "varchar(10)")
    private String month;

    @Column(columnDefinition = "varchar(10)")
    private String year;

    @NotNull
    private Double amount;

    @Column(columnDefinition = "varchar(10)")
    private String currency;

    @Column(columnDefinition = "varchar(255)")
    private String transactionId;

    @Column(columnDefinition = "varchar(20)")
    private String status;

    private LocalDateTime paymentDate;

    @JsonIgnore
    private String redirectToCompletePayment;

    @OneToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    @JsonIgnore
    private UserSubscription userSubscription;
}
