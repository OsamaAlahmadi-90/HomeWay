package com.example.homeway.Repository;

import com.example.homeway.Model.Payment;
import com.example.homeway.Model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findPaymentByUserSubscription(UserSubscription userSubscription);

    Payment findPaymentByTransactionId(String transactionId);
}
