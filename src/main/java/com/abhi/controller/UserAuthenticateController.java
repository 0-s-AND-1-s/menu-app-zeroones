package com.abhi.controller;

import com.abhi.pojo.RegisterUser;
import com.abhi.service.IRegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserAuthenticateController {


    private IRegisterUserService registerUserService;

    @Autowired
    public UserAuthenticateController(IRegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
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

    /*  Not Required these forms as we're going with Stateless
    @GetMapping("/signIn")
    public String showLoginPage(){
        return "signin";
    }*/
}
