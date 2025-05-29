package com.ne.domain.repositories;

import com.ne.domain.model.Deduction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface DeductionRepository extends JpaRepository<Deduction, String> {
    Optional<Deduction> findByDeductionName(String name);
}
