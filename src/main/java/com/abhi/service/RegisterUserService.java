package com.abhi.service;

import com.abhi.dto.request.RegisterUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface RegisterUserService extends UserDetailsService {
    String registerUser(RegisterUser userDTO);
}
