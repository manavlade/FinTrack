package com.fintrack.FinTrack.DTO;

public class AuthResponse {

    private final String token;

    public AuthResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

}
