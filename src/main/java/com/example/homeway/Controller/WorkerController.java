package com.example.homeway.Controller;


import com.example.homeway.API.ApiResponse;
import com.example.homeway.DTO.In.WorkerDTOIn;
import com.example.homeway.Model.User;
import com.example.homeway.Model.Worker;
import com.example.homeway.Service.WorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    // =====================================
    // ROLE: COMPANY
    // Description: Get all workers for the logged-in company
    // =====================================
    @GetMapping("/get-my-workers")
    public ResponseEntity<List<Worker>> getAllWorkers(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(workerService.getCompanyWorkers(user));
    }

    // =====================================
    // ROLE: COMPANY
    // Description: Update worker user information
    // =====================================
    @PutMapping("/update/{workerId}")
    public ResponseEntity<?> updateWorker(@AuthenticationPrincipal User user, @PathVariable Integer workerId, @RequestBody @Valid WorkerDTOIn workerDTOIn) {
        workerService.updateWorker(user, workerId, workerDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Worker updated successfully"));
    }

    // =====================================
    // ROLE: COMPANY
    // Description: Delete a worker that belongs to the company
    // =====================================
    @DeleteMapping("/delete/{workerId}")
    public ResponseEntity<ApiResponse> deleteWorker(@AuthenticationPrincipal User user, @PathVariable Integer workerId) {
        workerService.deleteWorker(user, workerId);
        return ResponseEntity.status(200).body(new ApiResponse("Worker deleted successfully"));
    }

    @PutMapping("/activate/{workerId}")
    public ResponseEntity<?> activateWorker(@AuthenticationPrincipal User user,@PathVariable Integer workerId) {
        workerService.activateWorker(user,workerId);
        return ResponseEntity.status(200).body(new ApiResponse("Worker activated successfully"));
    }


    @PutMapping("/deactivate/{workerId}")
    public ResponseEntity<String> deactivateWorker(@AuthenticationPrincipal User user, @PathVariable Integer workerId) {
        workerService.deactivateWorker(user, workerId);
        return ResponseEntity.status(200).body("Worker deactivated");
    }
}
