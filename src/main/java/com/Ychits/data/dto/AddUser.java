package com.Ychits.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AddUser {
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String address;
    private String userType;
}
