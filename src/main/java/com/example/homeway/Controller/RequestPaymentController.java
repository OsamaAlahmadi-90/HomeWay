package com.example.homeway.Controller;

import com.example.homeway.API.ApiResponse;
import com.example.homeway.Model.PaymentRequest;
import com.example.homeway.Model.User;
import com.example.homeway.Service.RequestPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class RequestPaymentController {
    private final RequestPaymentService requestPaymentService;

    @PostMapping("/offer/{offerId}")
    public ResponseEntity<?> payOffer(@AuthenticationPrincipal User user, @PathVariable Integer offerId, @RequestBody PaymentRequest paymentRequest) {
        return requestPaymentService.payOffer(user, offerId, paymentRequest);
    }

    @GetMapping("/callBack")
    public ResponseEntity<?> callBack(@RequestParam Integer offerId, @RequestParam String id) {
        requestPaymentService.confirmOfferPayment(offerId, id);

        return ResponseEntity.status(200).body(new ApiResponse("Payment verified and request marked as paid"));
    }

    // just for testing
    @GetMapping("/status/{paymentId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String paymentId) {
        return ResponseEntity.status(200).body(requestPaymentService.getPaymentStatus(paymentId));
    }

    //just for testing
    @PostMapping("/test/mark-paid/{requestId}")
    public ResponseEntity<?> markRequestPaid(@PathVariable Integer requestId) {
        requestPaymentService.markRequestPaid(requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Request marked as paid (testing only)"));
    }

}
