package com.ne.domain.controllers;

import com.ne.domain.dto.request.PayrollRequestDto;
import com.ne.domain.dto.response.PayslipResponseDto;
import com.ne.domain.services.PayrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/payslips")
@RequiredArgsConstructor
public class PayslipController {

    private final PayrollService payrollService;

    // Manager triggers payroll generation
    @PostMapping("/generate")
    public ResponseEntity<List<PayslipResponseDto>> generatePayroll(@Valid @RequestBody PayrollRequestDto dto) {
        List<PayslipResponseDto> payslips = payrollService.generatePayroll(dto);
        return ResponseEntity.ok(payslips);
    }

    // Employee views their payslip(s) for a month and year
    @GetMapping("/me")
    public ResponseEntity<List<PayslipResponseDto>> getMyPayslips(
            Principal principal,
            @RequestParam int month,
            @RequestParam int year) {
        // Assuming principal.getName() returns employeeCode
        List<PayslipResponseDto> payslips = payrollService.getPayslipsForEmployee(principal.getName(), month, year);
        return ResponseEntity.ok(payslips);
    }

    // Manager views all payslips for a month and year
    @GetMapping
    public ResponseEntity<List<PayslipResponseDto>> getAllPayslips(
            @RequestParam int month,
            @RequestParam int year) {
        List<PayslipResponseDto> payslips = payrollService.getAllPayslips(month, year);
        return ResponseEntity.ok(payslips);
    }
}
