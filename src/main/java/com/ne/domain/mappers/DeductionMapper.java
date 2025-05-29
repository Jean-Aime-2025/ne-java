package com.ne.domain.mappers;

import com.ne.domain.dto.request.DeductionRequestDto;
import com.ne.domain.dto.response.DeductionResponseDto;
import com.ne.domain.model.Deduction;

import java.util.UUID;

public class DeductionMapper {

    public static Deduction toEntity(DeductionRequestDto dto) {
        return Deduction.builder()
                .code(UUID.randomUUID().toString())
                .deductionName(dto.getDeductionName())
                .percentage(dto.getPercentage())
                .build();
    }

    public static DeductionResponseDto toResponseDto(Deduction deduction) {
        return DeductionResponseDto.builder()
                .code(deduction.getCode())
                .deductionName(deduction.getDeductionName())
                .percentage(deduction.getPercentage())
                .build();
    }
}
