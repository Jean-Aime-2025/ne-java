package com.ne.domain.repositories;

import com.ne.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByEmail(String email);
}