package com.example.homeway.Controller;

import com.example.homeway.API.ApiResponse;
import com.example.homeway.DTO.In.NotificationDTOIn;
import com.example.homeway.Model.User;
import com.example.homeway.Service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    // ROLE: ADMIN
    // Description: Get all notifications
    @GetMapping("/admin/get-all")
    public ResponseEntity<?> getAllNotifications() {return ResponseEntity.status(200).body(notificationService.getAllNotifications());
    }

    // ROLE: ADMIN
    // Description: Get all customer notifications
    @GetMapping("/admin/get-all-customers")
    public ResponseEntity<?> getAllCustomerNotifications() {
        return ResponseEntity.status(200).body(notificationService.getAllCustomerNotifications());
    }


    // ROLE: COMPANY
    // Description: Get my notifications
    @GetMapping("/company/get-my")
    public ResponseEntity<?> getCompanyNotifications(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(notificationService.getMyNotificationsCompany(user));
    }

    //  COMPANY
    // Description: Send notification to customer
    @PostMapping("/company/send/{customerId}")
    public ResponseEntity<?> sendNotificationToCustomer(@AuthenticationPrincipal User user, @PathVariable Integer customerId, @RequestBody @Valid NotificationDTOIn dto) {
        notificationService.sendNotificationToCustomer(user, customerId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Notification sent to customer"));
    }

    //  COMPANY
    // Description: Delete my notification
    @DeleteMapping("/company/delete/{notificationId}")
    public ResponseEntity<?> deleteCompanyNotification(@AuthenticationPrincipal User user, @PathVariable Integer notificationId) {
        notificationService.deleteCompanyNotification(user, notificationId);
        return ResponseEntity.status(200).body(new ApiResponse("Notification deleted successfully"));
    }



    //  CUSTOMER
    // Description: Send notification to company
    @PostMapping("/customer/send/{companyId}")
    public ResponseEntity<?> sendNotificationToCompany(@AuthenticationPrincipal User user, @PathVariable Integer companyId, @RequestBody @Valid NotificationDTOIn dto)
    {
        notificationService.sendNotificationToCompany(user, companyId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Notification sent to company"));
    }

    //CUSTOMER
    // Description: Delete my notification
    @DeleteMapping("/customer/delete/{notificationId}")
    public ResponseEntity<?> deleteCustomerNotification(@AuthenticationPrincipal User user, @PathVariable Integer notificationId) {
        notificationService.deleteCustomerNotification(user, notificationId);
        return ResponseEntity.status(200).body(new ApiResponse("Notification deleted successfully"));
    }
}
