package com.Ychits.util.jwt;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String passWord;
}
