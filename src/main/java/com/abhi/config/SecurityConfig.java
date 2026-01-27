package com.abhi.config;

import com.abhi.filters.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder encoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder encoder, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Autowired
    public void authenticationDetails(AuthenticationManagerBuilder manager) throws Exception {
        manager.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain customizeSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                //To handle the invalid token that is when user is not authenticated
                        exceptionHandling(
                        ex -> ex.authenticationEntryPoint(
                                (request, response, authException) -> {
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                    response.getWriter().write("\"{\\\"error\\\":\\\"UNAUTHORIZED\\\",\\\"message\\\":\\\"Missing or invalid token\\\"}\"");
                                }))
                .authorizeHttpRequests(authenticate ->
                        authenticate.requestMatchers("/users/authenticate").permitAll()
                                .requestMatchers("/api/v1/createItem", "/api/v1/updateItem/**", "/api/v1/deleteItem/**")
                                .hasAuthority("ROLE_ADMIN").requestMatchers("/api/v1/getAllItems", "/api/v1/getItem/**")
                                .hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN")
                                .anyRequest().authenticated());
        // we don't need httpBasic now; JWT handles auth
        //.httpBasic(Customizer.withDefaults())


        // 🧩 Add JWT filter before UsernamePasswordAuthenticationFilter
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

   /* /authenticate is not mentioned, it falls under:
      .anyRequest().permitAll()*/

}

