package com.example.homeway.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserSubscription {

    @Id
    private Integer id;

    @NotEmpty(message = "Plan type cannot be null")
    @Pattern(regexp = "FREE|AI", message = "Plan type must be: FREE or AI")
    @Column(columnDefinition = "varchar(10) not null")
    private String planType;


    @NotEmpty(message = "Status cannot be null")
    @Pattern(
            regexp = "FREE_PLAN|PENDING|ACTIVE|EXPIRED|CANCELLED",
            message = "Status must be: FREE_PLAN, PENDING, ACTIVE, EXPIRED, or CANCELLED"
    )
    @Column(columnDefinition = "varchar(20) not null")
    private String status;

    @NotNull
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime startDate;

    @Column(columnDefinition = "datetime")
    private LocalDateTime endDate;

    @Column(columnDefinition = "datetime")
    private LocalDateTime nextBillingDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(columnDefinition = "decimal(10,2)")
    private Double monthlyPrice;

    @Column(name = "expiry_email_sent", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean expiryEmailSent = false;

    @Column(name = "renewal_email_sent", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean renewalEmailSent = false;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "userSubscription", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private SubscriptionPayment payment;


}
