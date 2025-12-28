package com.example.homeway.Controller;

import com.example.homeway.API.ApiResponse;
import com.example.homeway.DTO.In.ReportDTOIn;
import com.example.homeway.DTO.In.ReportUpdateDTOIn;
import com.example.homeway.Model.Report;
import com.example.homeway.Model.User;
import com.example.homeway.Service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    // Worker (assigned)
    @GetMapping("/get/{reportId}")
    public ResponseEntity<?> getReport(@AuthenticationPrincipal User user, @PathVariable Integer reportId) {
        Report report = reportService.getReport(user, reportId);
        return ResponseEntity.status(200).body(report);
    }


    // WORKER only
    @PostMapping("/add/{requestId}")
    public ResponseEntity<?> addReport(@AuthenticationPrincipal User user, @PathVariable Integer requestId, @RequestBody @Valid ReportDTOIn dto) {
        reportService.addReport(user, requestId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Report added successfully"));
    }


    // WORKER only (must own request)
    @PutMapping("/update/{reportId}")
    public ResponseEntity<?> updateReport(@AuthenticationPrincipal User user, @PathVariable Integer reportId, @RequestBody @Valid ReportUpdateDTOIn dto) {
        reportService.updateReport(user, reportId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Report updated successfully"));
    }

    // WORKER only (must own request)
    @DeleteMapping("/delete/{reportId}")
    public ResponseEntity<?> deleteReport(@AuthenticationPrincipal User user, @PathVariable Integer reportId) {
        reportService.deleteReport(user, reportId);
        return ResponseEntity.status(200).body(new ApiResponse("Report deleted successfully"));
    }

    //extra endpoints

    // Worker OR Customer
    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getReportsByRequest(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        List<Report> reports = reportService.getReportsByRequest(user, requestId);
        return ResponseEntity.status(200).body(reports);
    }


    // Worker OR Customer
    @GetMapping("/read/{reportId}")
    public ResponseEntity<?> getReportForRead(@AuthenticationPrincipal User user, @PathVariable Integer reportId) {
        Report report = reportService.getReportForRead(user, reportId);
        return ResponseEntity.status(200).body(report);
    }


}