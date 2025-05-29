package com.ne.domain.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PayrollApprovalRequest(
        @Min(1) @Max(12) int month,
        @Min(2000) int year,
        @NotBlank String institutionName
) {}
