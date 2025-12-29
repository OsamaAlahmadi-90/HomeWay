package com.example.homeway.Controller;

import com.example.homeway.API.ApiResponse;
import com.example.homeway.Model.SubscriptionPayment;
import com.example.homeway.Model.User;
import com.example.homeway.Service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class UserSubscriptionController {

    private final UserSubscriptionService subscriptionService;

    // ADMIN ONLY - Get all subscriptions
    @GetMapping("/get")
    public ResponseEntity<?> getAllSubscriptions() {
        return ResponseEntity.status(200).body(subscriptionService.getAllSubscriptions());
    }

    // USER ONLY - Subscribe to AI plan
    @PostMapping("/add/plan-type/{planType}")
    public ResponseEntity<?> addSubscription(@AuthenticationPrincipal User user, @PathVariable String planType, @RequestBody SubscriptionPayment payment) {

        return ResponseEntity.status(200).body(subscriptionService.addSubscription(user.getId(), planType, payment));
    }

    // USER ONLY - Cancel own subscription
    @PutMapping("/cancel-subscription/{subscriptionId}")
    public ResponseEntity<?> cancelSubscription(@AuthenticationPrincipal User user, @PathVariable Integer subscriptionId) {

        subscriptionService.cancelSubscription(user.getId(), subscriptionId);
        return ResponseEntity.status(200).body(new ApiResponse("Subscription has been cancelled successfully"));
    }

    // USER ONLY - Activate cancelled subscription
    @PutMapping("/activate-subscription/{subscriptionId}")
    public ResponseEntity<?> activateSubscription(@AuthenticationPrincipal User user, @PathVariable Integer subscriptionId) {

        subscriptionService.activateSubscription(user.getId(), subscriptionId);
        return ResponseEntity.status(200).body(new ApiResponse("Subscription has been activated successfully"));
    }

    // USER ONLY - Renew subscription
    @PutMapping("/renew")
    public ResponseEntity<?> renewSubscription(@AuthenticationPrincipal User user, @RequestBody SubscriptionPayment payment) {

        return ResponseEntity.status(200).body(subscriptionService.renewSubscription(user.getId(), payment));
    }

    // USER ONLY - Get payment / subscription history
    @GetMapping("/history-payment")
    public ResponseEntity<?> historySubscriptions(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(subscriptionService.historyOfSubscription(user.getId()));
    }

    // USER ONLY - Get subscription dashboard
    @GetMapping("/manage-subscription")
    public ResponseEntity<?> getSubscriptionDashboard(@AuthenticationPrincipal User user) {

        Map<String, Object> dashboard = subscriptionService.getSubscriptionDashboard(user.getId());

        return ResponseEntity.status(200).body(dashboard);
    }
}
