package com.sanvi.ecom_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private Integer id;
    private String name;
    private String email;
    private String role;
    private String token;
}