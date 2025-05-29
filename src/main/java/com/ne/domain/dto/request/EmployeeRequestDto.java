package com.ne.domain.dto.request;


import com.ne.domain.enums.EmployeeStatus;
import com.ne.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @Builder.Default
    @Schema(hidden = true)
    private Set<Role> roles = Set.of(Role.EMPLOYEE);

    private String mobile;

    @NotNull
    private LocalDate dateOfBirth;

    @Schema(hidden = true)
    private EmployeeStatus status;
}
