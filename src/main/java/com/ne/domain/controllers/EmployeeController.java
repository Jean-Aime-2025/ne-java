package com.ne.domain.controllers;

import com.ne.domain.auth.AuthService;
import com.ne.domain.dto.request.EmployeeRequestDto;
import com.ne.domain.dto.request.LoginRequestDto;
import com.ne.domain.dto.response.EmployeeResponseDto;
import com.ne.domain.dto.response.LoginResponse;
import com.ne.domain.services.EmployeeService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto dto) {
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{code}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeByCode(@PathVariable String code) {
        return ResponseEntity.ok(employeeService.getByCode(code));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteEmployeeByCode(@PathVariable String code) {
        employeeService.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    @RateLimiter(name = "auth-rate-limiter")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequestDto request,
            HttpServletResponse response
    ) {
        var result = authService.login(request, response);
        return ResponseEntity.ok(new LoginResponse(result.accessToken()));
    }
}
