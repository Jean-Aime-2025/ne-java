package com.ne.domain.dto.response;

import com.ne.domain.enums.EmploymentStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploymentResponseDto {
    private String code;
    private String employeeCode;
    private String department;
    private String position;
    private double baseSalary;
    private EmploymentStatus status;
    private LocalDate joiningDate;
}
