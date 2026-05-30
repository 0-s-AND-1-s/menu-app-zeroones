package com.abhi.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String userName;
    private String password;
}
