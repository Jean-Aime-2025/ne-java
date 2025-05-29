package com.ne.domain.services;


import com.ne.domain.commons.exceptions.BadRequestException;
import com.ne.domain.commons.exceptions.NotFoundException;
import com.ne.domain.dto.request.EmployeeRequestDto;
import com.ne.domain.dto.response.EmployeeResponseDto;
import com.ne.domain.enums.EmployeeStatus;
import com.ne.domain.enums.Role;
import com.ne.domain.mappers.EmployeeMapper;
import com.ne.domain.model.Employee;
import com.ne.domain.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeResponseDto createEmployee(EmployeeRequestDto dto) {
        if (employeeRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email is already in use");
        }

        // Set default role if none provided
        if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
            dto.setRoles(Set.of(Role.EMPLOYEE));
        }

        // Set default status if null
        if (dto.getStatus() == null) {
            dto.setStatus(EmployeeStatus.ACTIVE);
        }

        Employee employee = EmployeeMapper.toEntity(dto);
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        return EmployeeMapper.toResponseDTO(employeeRepository.save(employee));
    }



    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDto getByCode(String code) {
        return employeeRepository.findById(code)
                .map(EmployeeMapper::toResponseDTO)
                .orElseThrow(() -> new NotFoundException("Employee not found with code: " + code));
    }

    public void deleteByCode(String code) {
        if (!employeeRepository.existsById(code)) {
            throw new NotFoundException("Employee not found with code: " + code);
        }
        employeeRepository.deleteById(code);
    }

    public Employee getByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Employee not found with email: " + email));
    }
}
