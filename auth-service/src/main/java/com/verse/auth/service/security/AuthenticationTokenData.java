package com.verse.auth.service.security;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationTokenData {
    private String userId;
    private String deviceId;
    private String userAudience;
    private String token;
}
