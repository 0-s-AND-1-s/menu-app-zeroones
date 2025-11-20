package com.abhi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }


    @Autowired
    public void authenticationDetails(AuthenticationManagerBuilder manager) throws Exception {
        manager.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Bean
    public SecurityFilterChain customizeSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authorizeHttpRequests(authenticate ->
                        authenticate.requestMatchers("/api/v1/createItem", "/api/v1/updateItem/**", "/api/v1/deleteItem/**")
                                .hasAuthority("ROLE_ADMIN").requestMatchers("/api/v1/getAllItems", "/api/v1/getItem/**")
                                .hasAuthority("ROLE_CUSTOMER")
                                .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

}

