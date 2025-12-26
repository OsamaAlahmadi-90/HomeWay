package com.example.homeway.Controller;
import com.example.homeway.API.ApiResponse;
import com.example.homeway.Model.User;
import com.example.homeway.Service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/get/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable Integer companyId) {
        return ResponseEntity.ok(companyService.getCompanyById(companyId));
    }

    @GetMapping("/get-by-role/{role}")
    public ResponseEntity<?> getCompaniesByRole(@PathVariable String role) {
        return ResponseEntity.ok(companyService.getCompaniesByRole(role));
    }

    //inspection
    @PutMapping("/inspection/approve/{requestId}/price/{price}")
    public ResponseEntity<?> approveInspectionRequest(@AuthenticationPrincipal User user, @PathVariable Integer requestId, @PathVariable Double price) {
        companyService.approveInspectionRequest(user, requestId, price);
        return ResponseEntity.ok(new ApiResponse("request approved + offer created"));
    }

    @PutMapping("/inspection/start/{requestId}")
    public ResponseEntity<?> startInspectionRequest(@AuthenticationPrincipal User user,@PathVariable Integer requestId) {
        companyService.startInspectionRequest(user, requestId);
        return ResponseEntity.ok(new ApiResponse("request started (in_progress)"));
    }

    @PutMapping("/inspection/complete/{requestId}")
    public ResponseEntity<?> completeInspectionRequest(@AuthenticationPrincipal User user,@PathVariable Integer requestId) {
        companyService.completeInspectionRequest(user, requestId);
        return ResponseEntity.ok(new ApiResponse("request completed"));
    }

    @PutMapping("/inspection/reject/{requestId}")
    public ResponseEntity<?> rejectInspectionRequest(@AuthenticationPrincipal User user,@PathVariable Integer requestId) {
        companyService.rejectInspectionRequest(user, requestId);
        return ResponseEntity.ok(new ApiResponse("request rejected"));
    }

}
