package com.example.homeway.Controller;


import com.example.homeway.API.ApiResponse;
import com.example.homeway.DTO.In.PropertyDTOIn;
import com.example.homeway.Model.Property;
import com.example.homeway.Model.User;
import com.example.homeway.Service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    // =====================================
    // ROLE: CUSTOMER
    // Description: Customer can add a new property
    // =====================================
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProperty(@AuthenticationPrincipal User user, @RequestBody @Valid PropertyDTOIn propertyDTOIn
    ) {
        propertyService.createProperty(user, propertyDTOIn);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Property created successfully"));
    }

    // =====================================
    // ROLE: CUSTOMER
    // Description: Customer can retrieve all his properties
    // =====================================
    @GetMapping("/my")
    public ResponseEntity<List<Property>> getMyProperties(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200)
                .body(propertyService.getMyProperties(user));
    }

    // =====================================
    // ROLE: CUSTOMER
    // Description: Customer can update his own property only
    // =====================================
    @PutMapping("/update/{propertyId}")
    public ResponseEntity<ApiResponse> updateProperty(@AuthenticationPrincipal User user, @PathVariable Integer propertyId, @RequestBody @Valid PropertyDTOIn propertyDTOIn) {
        propertyService.updateProperty(user, propertyId, propertyDTOIn);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Property updated successfully"));
    }

    // =====================================
    // ROLE: CUSTOMER
    // Description: Customer can delete his own property only
    // =====================================
    @DeleteMapping("/delete/{propertyId}")
    public ResponseEntity<ApiResponse> deleteProperty(@AuthenticationPrincipal User user, @PathVariable Integer propertyId) {
        propertyService.deleteProperty(user, propertyId);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Property deleted successfully"));
    }
}