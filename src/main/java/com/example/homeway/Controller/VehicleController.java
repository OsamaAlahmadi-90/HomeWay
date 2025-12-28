package com.example.homeway.Controller;

import com.example.homeway.API.ApiResponse;
import com.example.homeway.Model.Request;
import com.example.homeway.Model.User;
import com.example.homeway.Model.Vehicle;
import com.example.homeway.Service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


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

    //Extra endpoints
    @GetMapping("/my")
    public ResponseEntity<?> getMyVehicles(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(vehicleService.getMyVehicles(user));
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<?> getVehicleDetails(@AuthenticationPrincipal User user, @PathVariable Integer vehicleId) {
        return ResponseEntity.status(200).body(vehicleService.getVehicleDetails(user, vehicleId));
    }

    @GetMapping("/my/available/{available}")
    public ResponseEntity<?> getMyVehiclesByAvailability(@AuthenticationPrincipal User user, @PathVariable Boolean available) {
        return ResponseEntity.status(200).body(vehicleService.getVehiclesByAvailability(user, available));
    }


    @GetMapping("/my/type/{type}")
    public ResponseEntity<?> getMyVehiclesByType(@AuthenticationPrincipal User user, @PathVariable String type) {
        return ResponseEntity.status(200).body(vehicleService.getMyVehiclesByType(user, type));
    }


    @GetMapping("/my/min-capacity/{minCapacity}")
    public ResponseEntity<?> getMyVehiclesByMinCapacity(@AuthenticationPrincipal User user, @PathVariable Double minCapacity) {
        return ResponseEntity.status(200).body(vehicleService.getMyVehiclesByMinCapacity(user, minCapacity));
    }


    @GetMapping("/{vehicleId}/requests")
    public ResponseEntity<?> getVehicleRequestHistory(@AuthenticationPrincipal User user, @PathVariable Integer vehicleId) {
        Set<Request> requests = vehicleService.getVehicleRequestHistory(user, vehicleId);
        return ResponseEntity.status(200).body(requests);
    }
}
