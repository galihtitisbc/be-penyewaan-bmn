package com.badminton.backend.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TransactionTokenResponse {
    private String token;
    @JsonAlias("redirect_url")
    private String redirectUrl;
}
