package com.Ychits.data.dto;

import lombok.Data;

@Data
public class AddChitUser {
    private String name;
    private String email;
    private String mobile;
    private String address;
    private String userType;
}
