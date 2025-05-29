package com.ne.domain.mappers;

import com.ne.domain.dto.request.EmploymentRequestDto;
import com.ne.domain.dto.response.EmploymentResponseDto;
import com.ne.domain.model.Employee;
import com.ne.domain.model.Employment;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class EmploymentMapper {

    public Employment toEntity(EmploymentRequestDto dto, Employee employee) {
        return Employment.builder()
                .code(UUID.randomUUID().toString())
                .employee(employee)
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .baseSalary(dto.getBaseSalary())
                .status(dto.getStatus())
                .joiningDate(dto.getJoiningDate())
                .build();
    }

    public EmploymentResponseDto toResponseDTO(Employment entity) {
        return EmploymentResponseDto.builder()
                .code(entity.getCode())
                .employeeCode(entity.getEmployee().getCode())
                .department(entity.getDepartment())
                .position(entity.getPosition())
                .baseSalary(entity.getBaseSalary())
                .status(entity.getStatus())
                .joiningDate(entity.getJoiningDate())
                .build();
    }
}
