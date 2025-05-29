package com.ne.domain.controllers;

import com.ne.domain.dto.request.EmploymentRequestDto;
import com.ne.domain.dto.response.EmploymentResponseDto;
import com.ne.domain.services.EmploymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employments")
@RequiredArgsConstructor
public class EmploymentController {

    private final EmploymentService employmentService;

    @PostMapping
    public ResponseEntity<EmploymentResponseDto> createEmployment(@Valid @RequestBody EmploymentRequestDto dto) {
        return ResponseEntity.ok(employmentService.createEmployment(dto));
    }

    @GetMapping
    public ResponseEntity<List<EmploymentResponseDto>> getAllEmployments() {
        return ResponseEntity.ok(employmentService.getAllEmployments());
    }

    @GetMapping("/{code}")
    public ResponseEntity<EmploymentResponseDto> getEmploymentByCode(@PathVariable String code) {
        return ResponseEntity.ok(employmentService.getByCode(code));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteEmploymentByCode(@PathVariable String code) {
        employmentService.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }
}
