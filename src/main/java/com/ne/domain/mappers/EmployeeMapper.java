package com.ne.domain.mappers;

import com.ne.domain.dto.request.EmployeeRequestDto;
import com.ne.domain.dto.response.EmployeeResponseDto;
import com.ne.domain.model.Employee;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequestDto dto) {
        return Employee.builder()
                .code(UUID.randomUUID().toString())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .roles(dto.getRoles())
                .mobile(dto.getMobile())
                .dateOfBirth(dto.getDateOfBirth())
                .status(dto.getStatus())
                .build();
    }

    public EmployeeResponseDto toResponseDTO(Employee entity) {
        return EmployeeResponseDto.builder()
                .code(entity.getCode())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .roles(entity.getRoles())
                .mobile(entity.getMobile())
                .dateOfBirth(entity.getDateOfBirth())
                .status(entity.getStatus())
                .build();
    }
}
