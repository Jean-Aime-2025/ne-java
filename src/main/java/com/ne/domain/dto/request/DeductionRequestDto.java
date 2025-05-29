package com.ne.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeductionRequestDto {

    @NotBlank(message = "Deduction name is required")
    private String deductionName;

    @DecimalMin(value = "0.0", inclusive = false, message = "Percentage must be greater than 0")
    @DecimalMax(value = "100.0", message = "Percentage must be less than or equal to 100")
    private double percentage;
}
