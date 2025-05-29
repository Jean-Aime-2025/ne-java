package com.ne.domain.dto.request;


import com.ne.domain.enums.EmploymentStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploymentRequestDto {

    @NotBlank
    private String employeeCode;

    @NotBlank
    private String department;

    @NotBlank
    private String position;

    @Positive
    private double baseSalary;

    @Builder.Default
    private EmploymentStatus status = EmploymentStatus.ACTIVE;

    @NotNull
    private LocalDate joiningDate;
}
