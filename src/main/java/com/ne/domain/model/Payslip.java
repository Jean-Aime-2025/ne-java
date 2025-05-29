package com.ne.domain.model;

import jakarta.persistence.*;
import lombok.*;

import com.ne.domain.enums.PayslipStatus;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payslip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    @Column(nullable = false)
    private double houseAmount;

    @Column(nullable = false)
    private double transportAmount;

    @Column(nullable = false)
    private double employeeTaxedAmount;

    @Column(nullable = false)
    private double pensionAmount;

    @Column(nullable = false)
    private double medicalInsuranceAmount;

    @Column(nullable = false)
    private double otherTaxedAmount;

    @Column(nullable = false)
    private double grossSalary;

    @Column(nullable = false)
    private double netSalary;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayslipStatus status;
}
