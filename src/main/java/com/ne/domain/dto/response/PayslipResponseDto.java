package com.ne.domain.dto.response;

import com.ne.domain.enums.PayslipStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayslipResponseDto {
    private Long id;
    private String employeeCode;
    private String employeeName;
    private double baseSalary;
    private double housingAmount;
    private double transportAmount;
    private double grossSalary;
    private double employeeTaxedAmount;
    private double pensionAmount;
    private double medicalInsuranceAmount;
    private double otherTaxedAmount;
    private double netSalary;
    private PayslipStatus status;
    private int month;
    private int year;
}
