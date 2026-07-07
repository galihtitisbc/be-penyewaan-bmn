package com.badminton.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
