package com.example.homeway.Controller;
import com.example.homeway.API.ApiResponse;
import com.example.homeway.DTO.In.CustomerDTOIn;
import com.example.homeway.Model.User;
import com.example.homeway.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity<?> getMyCustomer(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(customerService.getMyCustomer(user));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMyCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid CustomerDTOIn dto) {
        customerService.updateMyCustomer(user, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Customer updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMyCustomer(@AuthenticationPrincipal User user) {
        customerService.deleteMyCustomer(user);
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted successfully"));
    }
}
