package com.example.homeway.Repository;

import com.example.homeway.Model.SubscriptionPayment;
import com.example.homeway.Model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment, Integer> {

    SubscriptionPayment findPaymentByUserSubscription(UserSubscription userSubscription);

    SubscriptionPayment findPaymentByTransactionId(String transactionId);
}
