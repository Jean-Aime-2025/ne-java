package com.ne.domain.config;

import com.ne.domain.dto.request.EmployeeRequestDto;
import com.ne.domain.enums.Role;
import com.ne.domain.services.EmployeeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final EmployeeService employeeService;

    @PostConstruct
    public void seedUsers() {
            createIfNotExists("admin@ne.com", "Admin", "User", "admin123", Set.of(Role.ADMIN));
        createIfNotExists("manager@ne.com", "Manager", "User", "manager123", Set.of(Role.MANAGER));
        createIfNotExists("employee@ne.com", "Employee", "User", "employee123", Set.of(Role.EMPLOYEE));
    }

    private void createIfNotExists(String email, String firstName, String lastName, String password, Set<Role> roles) {
        try {
            EmployeeRequestDto dto = EmployeeRequestDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(password)
                    .mobile("0734567899")
                    .dateOfBirth(LocalDate.of(1990, 1, 1))
                    .roles(roles)
                    .build();
            employeeService.createEmployee(dto);
        } catch (Exception e) {
            System.out.println("User " + email + " already exists or error: " + e.getMessage());
        }
    }
}
