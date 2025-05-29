package com.ne.domain.dto.request;


import com.ne.domain.enums.EmployeeStatus;
import com.ne.domain.enums.Role;
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
    private Set<Role> roles = Set.of(Role.EMPLOYEE);

    private String mobile;

    @NotNull
    private LocalDate dateOfBirth;

    private EmployeeStatus status;
}
