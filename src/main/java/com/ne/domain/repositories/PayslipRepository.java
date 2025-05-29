package com.ne.domain.repositories;

import com.ne.domain.model.Employee;
import com.ne.domain.model.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PayslipRepository extends JpaRepository<Payslip, Long> {
    List<Payslip> findByEmployeeAndMonthAndYear(Employee employee, int month, int year);
    List<Payslip> findByMonthAndYear(int month, int year);
    boolean existsByEmployeeAndMonthAndYear(Employee employee, int month, int year);
}
