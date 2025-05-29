package com.ne.domain.services;

import com.ne.domain.dto.request.PayrollRequestDto;
import com.ne.domain.dto.response.PayslipResponseDto;
import com.ne.domain.enums.EmployeeStatus;
import com.ne.domain.enums.PayslipStatus;
import com.ne.domain.model.Deduction;
import com.ne.domain.model.Employee;
import com.ne.domain.model.Payslip;
import com.ne.domain.repositories.DeductionRepository;
import com.ne.domain.repositories.EmployeeRepository;
import com.ne.domain.repositories.PayslipRepository;
import com.ne.domain.mappers.PayslipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final EmployeeRepository employeeRepository;
    private final DeductionRepository deductionRepository;
    private final PayslipRepository payslipRepository;

    /**
     * Generate payroll for all active employees for given month, year and base salary
     */
    @Transactional
    public List<PayslipResponseDto> generatePayroll(PayrollRequestDto dto) {

        int month = dto.getMonth();
        int year = dto.getYear();
        double baseSalary = dto.getBaseSalary();

        // Fetch active employees
        List<Employee> activeEmployees = employeeRepository.findAll().stream()
                .filter(e -> e.getStatus() == EmployeeStatus.ACTIVE)
                .toList();

        // Fetch all deductions by name for easy access
        Map<String, Deduction> deductionsMap = deductionRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Deduction::getDeductionName, d -> d));

        List<Payslip> generatedPayslips = new ArrayList<>();

        for (Employee emp : activeEmployees) {
            // Check if payslip already exists
            if (payslipRepository.existsByEmployeeAndMonthAndYear(emp, month, year)) {
                // Skip or update? Here we skip
                continue;
            }

            // Calculate deduction amounts from base salary
            double housing = deductionsMap.getOrDefault("Housing", Deduction.builder().percentage(0).build()).getPercentage();
            double transport = deductionsMap.getOrDefault("Transport", Deduction.builder().percentage(0).build()).getPercentage();

            double housingAmount = baseSalary * housing / 100.0;
            double transportAmount = baseSalary * transport / 100.0;

            double grossSalary = baseSalary + housingAmount + transportAmount;

            // Individual deductions
            double employeeTax = deductionsMap.getOrDefault("Employee Tax", Deduction.builder().percentage(0).build()).getPercentage();
            double pension = deductionsMap.getOrDefault("Pension", Deduction.builder().percentage(0).build()).getPercentage();
            double medicalInsurance = deductionsMap.getOrDefault("Medical Insurance", Deduction.builder().percentage(0).build()).getPercentage();
            double others = deductionsMap.getOrDefault("Others", Deduction.builder().percentage(0).build()).getPercentage();

            double employeeTaxAmount = baseSalary * employeeTax / 100.0;
            double pensionAmount = baseSalary * pension / 100.0;
            double medicalInsuranceAmount = baseSalary * medicalInsurance / 100.0;
            double otherTaxedAmount = baseSalary * others / 100.0;

            double totalDeductions = employeeTaxAmount + pensionAmount + medicalInsuranceAmount + otherTaxedAmount;

            // Ensure deductions do not exceed gross salary
            if (totalDeductions > grossSalary) {
                throw new IllegalArgumentException("Total deductions exceed gross salary for employee " + emp.getCode());
            }

            double netSalary = grossSalary - totalDeductions;

            Payslip payslip = Payslip.builder()
                    .employee(emp)
                    .houseAmount(housingAmount)
                    .transportAmount(transportAmount)
                    .employeeTaxedAmount(employeeTaxAmount)
                    .pensionAmount(pensionAmount)
                    .medicalInsuranceAmount(medicalInsuranceAmount)
                    .otherTaxedAmount(otherTaxedAmount)
                    .grossSalary(grossSalary)
                    .netSalary(netSalary)
                    .month(month)
                    .year(year)
                    .status(PayslipStatus.PENDING)
                    .build();

            generatedPayslips.add(payslipRepository.save(payslip));
        }

        return generatedPayslips.stream()
                .map(PayslipMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Get payslips for a single employee for given month and year
     */
    public List<PayslipResponseDto> getPayslipsForEmployee(String employeeCode, int month, int year) {
        Employee emp = employeeRepository.findById(employeeCode)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));

        List<Payslip> payslips = payslipRepository.findByEmployeeAndMonthAndYear(emp, month, year);

        return payslips.stream()
                .map(PayslipMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Manager view: Get all payslips for a given month/year
     */
    public List<PayslipResponseDto> getAllPayslips(int month, int year) {
        List<Payslip> payslips = payslipRepository.findByMonthAndYear(month, year);

        return payslips.stream()
                .map(PayslipMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
