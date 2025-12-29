package com.example.homeway.Controller;

import com.example.homeway.API.ApiResponse;
import com.example.homeway.Model.User;
import com.example.homeway.Service.SubscriptionPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final SubscriptionPaymentService subscriptionPaymentService;

    @PostMapping("/confirm/{subscriptionId}/transaction/{transactionId}")
    public ResponseEntity<?> confirmPayment(@AuthenticationPrincipal User user, @PathVariable Integer subscriptionId, @PathVariable String transactionId) throws JsonProcessingException {

        subscriptionPaymentService.updateAndConfirmPayment(subscriptionId, transactionId, user.getId());

        return ResponseEntity.status(200).body(new ApiResponse("Payment confirmed"));
    }
}
