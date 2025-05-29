package com.ne.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollRequestDto {

    @Min(1)
    @Max(12)
    private int month;

    @Min(2000)
    private int year;

    @Positive(message = "Base salary must be positive")
    private double baseSalary;
}
