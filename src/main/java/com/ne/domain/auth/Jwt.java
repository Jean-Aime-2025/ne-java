package com.ne.domain.auth;

import com.ne.domain.enums.Role;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.Date;

public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;

    public Jwt(Claims claims, SecretKey secretKey) {
        this.claims = claims;
        this.secretKey = secretKey;
    }

    public boolean isExpired(){
        return claims.getExpiration().before(new Date());
    }

    public String getUserCode(){
        return claims.getSubject();
    }

    public Role getRole() {
        return Role.valueOf(claims.get("role", String.class));
    }

    @Override
    public String toString(){
        return io.jsonwebtoken.Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }
}
