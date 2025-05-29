package com.ne.domain.mappers;

import com.ne.domain.dto.response.PayslipResponseDto;
import com.ne.domain.model.Payslip;

public class PayslipMapper {
    public static PayslipResponseDto toResponseDto(Payslip payslip) {
        return PayslipResponseDto.builder()
                .id(payslip.getId())
                .employeeCode(payslip.getEmployee().getCode())
                .employeeName(payslip.getEmployee().getFirstName() + " " + payslip.getEmployee().getLastName())
                .baseSalary(payslip.getGrossSalary() - payslip.getHouseAmount() - payslip.getTransportAmount())
                .housingAmount(payslip.getHouseAmount())
                .transportAmount(payslip.getTransportAmount())
                .grossSalary(payslip.getGrossSalary())
                .employeeTaxedAmount(payslip.getEmployeeTaxedAmount())
                .pensionAmount(payslip.getPensionAmount())
                .medicalInsuranceAmount(payslip.getMedicalInsuranceAmount())
                .otherTaxedAmount(payslip.getOtherTaxedAmount())
                .netSalary(payslip.getNetSalary())
                .status(payslip.getStatus())
                .month(payslip.getMonth())
                .year(payslip.getYear())
                .build();
    }
}
