package com.example.homeway.Controller;

import com.example.homeway.API.ApiResponse;
import com.example.homeway.Model.User;
import com.example.homeway.Model.Vehicle;
import com.example.homeway.Service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllVehicles() {
        return ResponseEntity.status(200).body(vehicleService.getAllVehicles());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVehicle(@AuthenticationPrincipal User user, @RequestBody Vehicle vehicle) {
        vehicleService.addVehicle(user, vehicle);
        return ResponseEntity.status(200).body(new ApiResponse("Vehicle added successfully"));
    }

    @PutMapping("/update/{vehicleId}")
    public ResponseEntity<?> updateVehicleLimited(@AuthenticationPrincipal User user, @PathVariable Integer vehicleId, @RequestBody Vehicle vehicle) {
        vehicleService.updateVehicleLimited(user, vehicleId, vehicle);
        return ResponseEntity.status(200).body(new ApiResponse("Vehicle updated successfully"));
    }

    @DeleteMapping("/delete/{vehicleId}")
    public ResponseEntity<?> deleteVehicle(@AuthenticationPrincipal User user, @PathVariable Integer vehicleId) {
        vehicleService.deleteVehicle(user, vehicleId);
        return ResponseEntity.status(200).body(new ApiResponse("Vehicle deleted successfully"));
    }
}
