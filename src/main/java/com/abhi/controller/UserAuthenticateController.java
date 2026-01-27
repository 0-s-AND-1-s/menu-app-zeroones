package com.abhi.controller;

import CustomizedExceptions.AuthenticationErrorException;
import com.abhi.pojo.AuthenticationRequest;
import com.abhi.pojo.AuthenticationResponse;
import com.abhi.pojo.RegisterUser;
import com.abhi.service.IRegisterUserService;
import com.abhi.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserAuthenticateController {


    private IRegisterUserService registerUserService;

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    @Autowired
    public UserAuthenticateController(IRegisterUserService registerUserService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.registerUserService = registerUserService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/signUpForm")
    public String showSignUpForm() {
        return "signup";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterUser registerUser, Map<String, String> modelMap) {
        System.out.println("Implementation object of MODEL" + modelMap.getClass().getName());
        System.out.println("RegisterUser :: " + registerUser);
        String registerMsg = registerUserService.registerUser(registerUser);
        modelMap.put("register", registerMsg);
        return "register_success";
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

    /*  Not Required these forms as we're going with Stateless
    @GetMapping("/signIn")
    public String showLoginPage(){
        return "signin";
    }*/
}
