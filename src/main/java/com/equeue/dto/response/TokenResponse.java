package com.equeue.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType // Bearer
){}
