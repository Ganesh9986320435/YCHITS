package com.Ychits.util.jwt;

import lombok.Data;

@Data
public class LoginResponse {
    private String id;
    private String role;
    private String token;

    public LoginResponse(String id,String role, String token) {
        this.id=id;
        this.role=role;
        this.token=token;
    }
    public LoginResponse() {
    }
}
