package com.abhi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableMethodSecurity
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        System.out.println("AppConfig.encoder");
        return new BCryptPasswordEncoder();
    }

}
