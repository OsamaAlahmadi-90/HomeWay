package com.example.homeway.Controller;
import com.example.homeway.API.ApiResponse;
import com.example.homeway.DTO.Ai.DescriptionDTOIn;
import com.example.homeway.DTO.Ai.RepairChecklistDTOIn;
import com.example.homeway.DTO.In.CompanyDTOIn;
import com.example.homeway.DTO.In.CompanyStatusDTOIn;
import com.example.homeway.Model.User;
import com.example.homeway.Service.CompanyService;
import jakarta.validation.Valid;
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
        return ResponseEntity.status(200).body(companyService.getAllCompanies());
    }

    @GetMapping("/get/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable Integer companyId) {
        return ResponseEntity.status(200).body(companyService.getCompanyById(companyId));
    }

    @GetMapping("/get-by-role/{role}")
    public ResponseEntity<?> getCompaniesByRole(@PathVariable String role) {
        return ResponseEntity.status(200).body(companyService.getCompaniesByRole(role));
    }

    @PutMapping("/update/{companyId}")
    public ResponseEntity<?> updateCompany(@PathVariable Integer companyId, @RequestBody CompanyStatusDTOIn dto) {
        companyService.updateCompanyStatus(companyId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("company updated"));
    }

    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable Integer companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.status(200).body(new ApiResponse("company deleted"));
    }

    //inspection
    @PutMapping("/inspection/approve/{requestId}/price/{price}")
    public ResponseEntity<?> approveInspection(@AuthenticationPrincipal User user, @PathVariable Integer requestId, @PathVariable Double price) {
        companyService.approveInspectionRequest(user, requestId, price);
        return ResponseEntity.status(200).body(new ApiResponse("inspection request approved + offer created"));
    }

    @PutMapping("/inspection/start/{requestId}")
    public ResponseEntity<?> startInspection(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.startInspectionRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("inspection request started"));
    }

    @PutMapping("/inspection/complete/{requestId}")
    public ResponseEntity<?> completeInspection(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.completeInspectionRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("inspection request completed"));
    }

    @PutMapping("/inspection/reject/{requestId}")
    public ResponseEntity<?> rejectInspection(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.rejectInspectionRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("inspection request rejected"));
    }

    //moving
    @PutMapping("/moving/approve/{requestId}/price/{price}")
    public ResponseEntity<?> approveMoving(@AuthenticationPrincipal User user, @PathVariable Integer requestId, @PathVariable Double price) {
        companyService.approveMovingRequest(user, requestId, price);
        return ResponseEntity.status(200).body(new ApiResponse("moving request approved + offer created"));
    }

    @PutMapping("/moving/start/{requestId}")
    public ResponseEntity<?> startMoving(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.startMovingRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("moving request started"));
    }

    @PutMapping("/moving/complete/{requestId}")
    public ResponseEntity<?> completeMoving(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.completeMovingRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("moving request completed"));
    }

    @PutMapping("/moving/reject/{requestId}")
    public ResponseEntity<?> rejectMoving(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.rejectMovingRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("moving request rejected"));
    }

    //Redesign
    @PutMapping("/redesign/approve/{requestId}/price/{price}")
    public ResponseEntity<?> approveRedesign(@AuthenticationPrincipal User user, @PathVariable Integer requestId, @PathVariable Double price) {
        companyService.approveRedesignRequest(user, requestId, price);
        return ResponseEntity.status(200).body(new ApiResponse("redesign request approved + offer created"));
    }

    @PutMapping("/redesign/start/{requestId}")
    public ResponseEntity<?> startRedesign(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.startRedesignRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("redesign request started"));
    }

    @PutMapping("/redesign/complete/{requestId}")
    public ResponseEntity<?> completeRedesign(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.completeRedesignRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("redesign request completed"));
    }

    @PutMapping("/redesign/reject/{requestId}")
    public ResponseEntity<?> rejectRedesign(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.rejectRedesignRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("redesign request rejected"));
    }

    //Maintenance
    @PutMapping("/maintenance/approve/{requestId}/price/{price}")
    public ResponseEntity<?> approveMaintenance(@AuthenticationPrincipal User user, @PathVariable Integer requestId, @PathVariable Double price) {
        companyService.approveMaintenanceRequest(user, requestId, price);
        return ResponseEntity.status(200).body(new ApiResponse("maintenance request approved + offer created"));
    }

    @PutMapping("/maintenance/start/{requestId}")
    public ResponseEntity<?> startMaintenance(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.startMaintenanceRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("maintenance request started"));
    }

    @PutMapping("/maintenance/complete/{requestId}")
    public ResponseEntity<?> completeMaintenance(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.completeMaintenanceRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("maintenance request completed"));
    }

    @PutMapping("/maintenance/reject/{requestId}")
    public ResponseEntity<?> rejectMaintenance(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        companyService.rejectMaintenanceRequest(user, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("maintenance request rejected"));
    }

    // ai
    @PostMapping("/worker/repair-checklist")
    public ResponseEntity<?> generateRepairChecklist(@AuthenticationPrincipal User user, @RequestBody @Valid RepairChecklistDTOIn dto) {
        return ResponseEntity.status(200).body(companyService.generateRepairChecklist(user, dto));
    }

    @PostMapping("/worker/safety-requirements")
    public ResponseEntity<?> workerSafetyRequirements(@AuthenticationPrincipal User user, @RequestBody @Valid DescriptionDTOIn dto) {
        return ResponseEntity.status(200).body(companyService.getWorkerSafetyRequirements(user, dto));
    }

    @GetMapping("/ai/cost-estimation/{requestId}")
    public ResponseEntity<?> companyServiceEstimationCost(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        String result = companyService.companyServiceEstimationCost(user, requestId);
        return ResponseEntity.status(200).body(result);
    }


    @PostMapping("/maintenance/spare-parts")
    public ResponseEntity<?> sparePartsEstimator(@AuthenticationPrincipal User user, @RequestBody @Valid DescriptionDTOIn dto) {
        String result = companyService.sparePartsEstimator(user, dto.getDescription());
        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/worker/issue-diagnosis")
    public ResponseEntity<?> issueDiagnosisAssistant(@AuthenticationPrincipal User user, @RequestBody @Valid DescriptionDTOIn dto ) {
        String result = companyService.issueDiagnosisAssistant(user, dto.getDescription());
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/moving/smart-planner/{requestId}")
    public ResponseEntity<?> smartMovePlanner(@AuthenticationPrincipal User user,@PathVariable Integer requestId) {
        String result = companyService.smartMovePlanner(user, requestId);
        return ResponseEntity.status(200).body(result);
    }


    @PostMapping("/worker/time-estimation")
    public ResponseEntity<?> timeEstimationHelper(@AuthenticationPrincipal User user, @RequestBody @Valid DescriptionDTOIn dto) {
        String result = companyService.timeEstimationHelper(user, dto.getDescription());
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/maintenance/plan/{requestId}")
    public ResponseEntity<?> maintenancePlan(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        String result = companyService.maintenancePlan(user, requestId);
        return ResponseEntity.status(200).body(result);
    }
}
