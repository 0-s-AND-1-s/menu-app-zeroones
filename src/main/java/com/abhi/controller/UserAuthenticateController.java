package com.abhi.controller;

import CustomizedExceptions.AuthenticationErrorException;
import com.abhi.dto.request.AuthenticationRequest;
import com.abhi.dto.request.RegisterUser;
import com.abhi.dto.response.AuthenticationResponse;
import com.abhi.service.RegisterUserService;
import com.abhi.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserAuthenticateController {


    private RegisterUserService registerUserService;

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    @Autowired
    public UserAuthenticateController(RegisterUserService registerUserService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.registerUserService = registerUserService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUser registerUser) {
        System.out.println("RegisterUser :: " + registerUser);
        return ResponseEntity.ok(registerUserService.registerUser(registerUser));

    }

    //Upon successful authentication, returning a JSON token
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
            System.out.println("-----------------------You're authentication is successful--------------------");
        } catch (BadCredentialsException exception) {
            System.out.println("Exception type ::" + exception.getClass().getName());
            throw new AuthenticationErrorException("Wrong credentials");
        }


        final UserDetails userDetails = registerUserService.loadUserByUsername(authenticationRequest.getUserName());

        final String jwtToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

}
