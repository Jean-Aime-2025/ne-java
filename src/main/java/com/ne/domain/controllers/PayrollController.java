package com.ne.domain.controllers;

import com.ne.domain.dto.request.PayrollApprovalRequest;
import com.ne.domain.services.PayrollApprovalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollApprovalService approvalService;

    @PostMapping("/approve")
    public ResponseEntity<String> approvePayroll(@Valid @RequestBody PayrollApprovalRequest request) {
        approvalService.approvePayroll(request.month(), request.year(), request.institutionName());
        return ResponseEntity.ok("Payroll approved and messages sent.");
    }

}
