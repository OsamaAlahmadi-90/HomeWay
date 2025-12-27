package com.example.homeway.Controller;


import com.example.homeway.API.ApiResponse;
import com.example.homeway.DTO.In.RequestCreateDTOIn;
import com.example.homeway.DTO.In.RequestUpdateDTOIn;
import com.example.homeway.Model.User;
import com.example.homeway.Service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    //admin
    @GetMapping("/get")
    public ResponseEntity<?> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/get/{requestId}")
    public ResponseEntity<?> getRequestById(@PathVariable Integer requestId) {
        return ResponseEntity.ok(requestService.getRequestById(requestId));
    }

    @GetMapping("/get-by-company/{companyId}")
    public ResponseEntity<?> getRequestsByCompany(@PathVariable Integer companyId) {
        return ResponseEntity.ok(requestService.getRequestsByCompany(companyId));
    }

    @GetMapping("/get-by-customer/{customerId}")
    public ResponseEntity<?> getRequestsByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(requestService.getRequestsByCustomer(customerId));
    }

    @PutMapping("/update/{requestId}")
    public ResponseEntity<?> updateRequest(@PathVariable Integer requestId, @Valid @RequestBody RequestUpdateDTOIn dto) {
        requestService.updateRequest(requestId, dto);
        return ResponseEntity.ok(new ApiResponse("request updated"));
    }

    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer requestId) {
        requestService.deleteRequest(requestId);
        return ResponseEntity.ok(new ApiResponse("request deleted"));
    }

    //Customer
    @PostMapping("/inspection/{propertyId}/{companyId}")
    public ResponseEntity<?> requestInspection(@AuthenticationPrincipal User user, @PathVariable Integer propertyId, @PathVariable Integer companyId, @Valid @RequestBody RequestCreateDTOIn dto) {
        requestService.requestInspection(user, propertyId, dto, companyId);
        return ResponseEntity.ok(new ApiResponse("inspection request created"));
    }

    @PostMapping("/moving/{propertyId}/{companyId}")
    public ResponseEntity<?> requestMoving(@AuthenticationPrincipal User user, @PathVariable Integer propertyId, @PathVariable Integer companyId, @Valid @RequestBody RequestCreateDTOIn dto) {
        requestService.requestMoveToHouse(user, propertyId, dto, companyId);
        return ResponseEntity.ok(new ApiResponse("moving request created"));
    }

    @PostMapping("/maintenance/{propertyId}/{companyId}")
    public ResponseEntity<?> requestMaintenance(@AuthenticationPrincipal User user, @PathVariable Integer propertyId, @PathVariable Integer companyId, @Valid @RequestBody RequestCreateDTOIn dto) {
        requestService.requestMaintenance(user, propertyId, dto, companyId);
        return ResponseEntity.ok(new ApiResponse("maintenance request created"));
    }

    @PostMapping("/redesign/{propertyId}/{companyId}")
    public ResponseEntity<?> requestRedesign(@AuthenticationPrincipal User user, @PathVariable Integer propertyId, @PathVariable Integer companyId, @Valid @RequestBody RequestCreateDTOIn dto) {
        requestService.requestResign(user, propertyId, dto, companyId);
        return ResponseEntity.ok(new ApiResponse("redesign request created"));
    }

}
