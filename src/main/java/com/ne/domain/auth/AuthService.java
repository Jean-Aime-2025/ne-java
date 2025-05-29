package com.ne.domain.auth;

import com.ne.domain.dto.request.LoginRequestDto;
import com.ne.domain.dto.response.LoginResponse;
import com.ne.domain.repositories.EmployeeRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequestDto loginRequest, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        var employee = employeeRepository.findByEmail(loginRequest.email()).orElseThrow();

        var accessToken = jwtService.generateAccessToken(employee);
        var refreshToken = jwtService.generateRefreshToken(employee);

        var cookie = new Cookie("refreshToken", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return new LoginResponse(accessToken.toString());
    }
}
