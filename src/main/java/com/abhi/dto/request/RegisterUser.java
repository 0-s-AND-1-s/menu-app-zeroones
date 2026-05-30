package com.abhi.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegisterUser {

    private String userName;
    private String pwd;
    private Set<String> authorityType;
}
