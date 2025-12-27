package com.example.homeway.Controller;


import com.example.homeway.API.ApiResponse;
import com.example.homeway.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/companies")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ROLE: ADMIN
    // Description: Approve a company
    @PutMapping("/approve/{companyId}")
    public ResponseEntity<?> approveCompany(@PathVariable Integer companyId) {
        adminService.approveCompany(companyId);
        return ResponseEntity.status(200).body(new ApiResponse("Company approved successfully"));
    }

    // ROLE: ADMIN
    // Description: Reject a company
    @PutMapping("/reject/{companyId}")
    public ResponseEntity<?> rejectCompany(@PathVariable Integer companyId) {
        adminService.rejectCompany(companyId);
        return ResponseEntity.status(200).body(new ApiResponse("Company rejected successfully"));
    }

    @GetMapping("/get-pending")
    public ResponseEntity<?> getPendingCompanies() {

        return ResponseEntity.status(200).body(adminService.getPendingCompanies());
    }
}
