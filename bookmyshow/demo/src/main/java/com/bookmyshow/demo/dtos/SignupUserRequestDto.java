package com.bookmyshow.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class SignupUserRequestDto {
    private String name;
    private String Email;
    private String password;
}
