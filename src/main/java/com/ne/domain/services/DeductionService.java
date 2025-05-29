package com.ne.domain.services;

import com.ne.domain.commons.exceptions.BadRequestException;
import com.ne.domain.commons.exceptions.NotFoundException;
import com.ne.domain.dto.request.DeductionRequestDto;
import com.ne.domain.dto.response.DeductionResponseDto;
import com.ne.domain.mappers.DeductionMapper;
import com.ne.domain.model.Deduction;
import com.ne.domain.repositories.DeductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeductionService {

    private final DeductionRepository deductionRepository;

    public DeductionResponseDto createDeduction(DeductionRequestDto dto) {
        deductionRepository.findByDeductionName(dto.getDeductionName()).ifPresent(d -> {
            throw new BadRequestException("Deduction with this name already exists");
        });

        Deduction deduction = DeductionMapper.toEntity(dto);
        return DeductionMapper.toResponseDto(deductionRepository.save(deduction));
    }

    public List<DeductionResponseDto> getAllDeductions() {
        return deductionRepository.findAll().stream()
                .map(DeductionMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public DeductionResponseDto getDeductionByCode(String code) {
        return deductionRepository.findById(code)
                .map(DeductionMapper::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Deduction not found"));
    }

    public void deleteByCode(String code) {
        if (!deductionRepository.existsById(code)) {
            throw new NotFoundException("Deduction not found");
        }
        deductionRepository.deleteById(code);
    }

}
