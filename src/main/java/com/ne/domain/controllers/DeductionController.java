package com.ne.domain.controllers;

import com.ne.domain.dto.request.DeductionRequestDto;
import com.ne.domain.dto.response.DeductionResponseDto;
import com.ne.domain.services.DeductionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deductions")
@RequiredArgsConstructor
public class DeductionController {

    private final DeductionService deductionService;

    @PostMapping
    public ResponseEntity<DeductionResponseDto> createDeduction(@Valid @RequestBody DeductionRequestDto dto) {
        return ResponseEntity.ok(deductionService.createDeduction(dto));
    }

    @GetMapping
    public ResponseEntity<List<DeductionResponseDto>> getAllDeductions() {
        return ResponseEntity.ok(deductionService.getAllDeductions());
    }

    @GetMapping("/{code}")
    public ResponseEntity<DeductionResponseDto> getDeductionByCode(@PathVariable String code) {
        return ResponseEntity.ok(deductionService.getDeductionByCode(code));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteDeduction(@PathVariable String code) {
        deductionService.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }
}
