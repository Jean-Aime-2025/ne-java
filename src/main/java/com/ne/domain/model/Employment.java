package com.ne.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.ne.domain.enums.EmploymentStatus;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Employment {
    @Id
    private String code;

    @ManyToOne
    private Employee employee;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private double baseSalary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentStatus status;

    @Column(nullable = false)
    private LocalDate joiningDate;
}
