package com.ne.domain.repositories;

import com.ne.domain.model.Employee;
import com.ne.domain.model.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface EmploymentRepository extends JpaRepository<Employment, String> {
    List<Employment> findByStatusTrue();
    Optional<Employment> findByEmployeeAndStatusTrue(Employee employee);
}