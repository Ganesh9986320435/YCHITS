package com.Ychits.util.jwt;

import lombok.Data;

@Data
public class LoginResponse {
    private String id;
    private String token;

    public LoginResponse(String id, String token) {
        this.id=id;
        this.token=token;
    }
    public LoginResponse() {
    }
}
