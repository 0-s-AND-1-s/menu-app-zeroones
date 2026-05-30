package com.abhi.service.impl;

import CustomizedExceptions.UserNotFoundException;
import com.abhi.repository.RegisterUserRepo;
import com.abhi.model.RegisterUserEntity;
import com.abhi.dto.request.RegisterUser;
import com.abhi.service.RegisterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    private RegisterUserRepo registerRepo;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public RegisterUserServiceImpl(RegisterUserRepo registerRepo, BCryptPasswordEncoder encoder) {
        this.registerRepo = registerRepo;
        this.encoder = encoder;
    }

    @Override
    public String registerUser(RegisterUser userDTO) {
        RegisterUserEntity registerEntity = new RegisterUserEntity();
        if (userDTO.getUserName() == null || userDTO.getUserName().isEmpty() || userDTO.getPwd() == null || userDTO.getPwd().trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Inputs");
        }
        userDTO.setPwd(encoder.encode(userDTO.getPwd()));
        System.out.println("RegisterUserServiceImpl.registerUser() USERDTO:: " + userDTO);
        BeanUtils.copyProperties(userDTO, registerEntity, "userId");
        System.out.println("RegisterUserServiceImpl.registerUser() REGISTERENTITY:: " + registerEntity);
        String userName = registerRepo.save(registerEntity).getUserName();
        return userName + " registered successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("RegisterUserServiceImpl.loadUserByUsername :: userName " + username);
        RegisterUserEntity registerUserEntity = registerRepo.findRegisterUserEntityByUserName(username).orElseThrow(() -> new UserNotFoundException(username));
        System.out.println("RegisterUserEntity from DB loadUserByUsername() ::" + registerUserEntity);
        Set<String> authority = registerUserEntity.getAuthorityType();
        return new User(username, registerUserEntity.getPwd(), authority.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
    }
}
