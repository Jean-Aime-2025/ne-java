package com.ne.domain.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeductionResponseDto {
    private String code;
    private String deductionName;
    private double percentage;
}
