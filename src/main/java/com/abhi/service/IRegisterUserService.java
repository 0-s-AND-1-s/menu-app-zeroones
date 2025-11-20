package com.abhi.service;

import com.abhi.pojo.RegisterUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IRegisterUserService extends UserDetailsService {
    String registerUser(RegisterUser userDTO);
}
