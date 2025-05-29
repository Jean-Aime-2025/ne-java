package com.ne.domain.dto.response;

import com.ne.domain.enums.EmployeeStatus;
import com.ne.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDto {
    private String code;
    private String firstName;
    private String lastName;
    private String email;

    @Schema(hidden = true)
    private Set<Role> roles;

    private String mobile;
    private LocalDate dateOfBirth;

    @Schema(hidden = true)
    private EmployeeStatus status;
}
