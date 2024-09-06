package com.generation.progetto_finale.auth.dto;

import lombok.Data;

@Data
public class AuthResponseDto 
{
    private String accessToken;
    private String username;
    private String tokenType = "Bearer ";
    private String role;

    public AuthResponseDto(String accessToken,String role, String username) 
    {
        this.accessToken = accessToken;
        this.username = username;
        this.role = role;
    }

    public AuthResponseDto(String accessToken) 
    {
        this.accessToken = accessToken;
    }
}
