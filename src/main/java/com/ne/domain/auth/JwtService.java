package com.ne.domain.auth;

import com.ne.domain.commons.exceptions.InvalidJwtException;
import com.ne.domain.model.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@AllArgsConstructor
@Service
@Slf4j
public class JwtService {

    private final JwtConfig config;

    public Jwt generateAccessToken(Employee employee) {
        return generateToken(employee, config.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(Employee employee) {
        return generateToken(employee, config.getRefreshTokenExpiration());
    }

    public Jwt parseToken(String token) {
        try {
            Claims claims = getClaims(token);
            return new Jwt(claims, (SecretKey) getSigningKey());
        } catch (ExpiredJwtException ex) {
            log.debug("Token expired: {}", ex.getMessage());
            throw new InvalidJwtException("Token expired");
        } catch (SignatureException ex) {
            log.debug("Invalid token signature: {}", ex.getMessage());
            throw new InvalidJwtException("Invalid token signature");
        } catch (JwtException | IllegalArgumentException ex) {
            log.debug("Invalid token: {}", ex.getMessage());
            throw new InvalidJwtException("Invalid token");
        }
    }

    private Jwt generateToken(Employee employee, long tokenExpiration) {
        Claims claims = Jwts.claims();
        claims.setSubject(employee.getCode());
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration));
        claims.put("email", employee.getEmail());

        String roleName = employee.getRoles().stream()
                .findFirst()
                .map(Enum::name)
                .orElse(null);
        claims.put("role", roleName);

        return new Jwt(claims, (SecretKey) getSigningKey());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(config.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
