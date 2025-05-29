package com.ne.domain.services;

import com.ne.domain.commons.exceptions.BadRequestException;
import com.ne.domain.commons.exceptions.NotFoundException;
import com.ne.domain.dto.request.EmploymentRequestDto;
import com.ne.domain.dto.response.EmploymentResponseDto;
import com.ne.domain.mappers.EmploymentMapper;
import com.ne.domain.model.Employment;
import com.ne.domain.model.Employee;
import com.ne.domain.repositories.EmployeeRepository;
import com.ne.domain.repositories.EmploymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploymentService {

    private final EmploymentRepository employmentRepository;
    private final EmployeeRepository employeeRepository;

    public EmploymentResponseDto createEmployment(EmploymentRequestDto dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeCode())
                .orElseThrow(() -> new NotFoundException("Employee not found with code: " + dto.getEmployeeCode()));

        boolean alreadyEmployed = employmentRepository.findAll().stream()
                .anyMatch(e -> e.getEmployee().getCode().equals(dto.getEmployeeCode()));
        if (alreadyEmployed) {
            throw new BadRequestException("This employee already has an employment record");
        }

        Employment employment = EmploymentMapper.toEntity(dto, employee);
        return EmploymentMapper.toResponseDTO(employmentRepository.save(employment));
    }

    public List<EmploymentResponseDto> getAllEmployments() {
        return employmentRepository.findAll().stream()
                .map(EmploymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EmploymentResponseDto getByCode(String code) {
        return employmentRepository.findById(code)
                .map(EmploymentMapper::toResponseDTO)
                .orElseThrow(() -> new NotFoundException("Employment not found with code: " + code));
    }

    public void deleteByCode(String code) {
        if (!employmentRepository.existsById(code)) {
            throw new NotFoundException("Employment not found with code: " + code);
        }
        employmentRepository.deleteById(code);
    }
}
