package com.ne.domain.dto.response;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
}
